package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.exception.InsufficientFundsException;
import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.repository.TransactionRepository;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class TransferMoneyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction transferMoneyWithCpf(TransferMoneyRequest transferMoneyRequest) {
        String fromCpf = SecurityContextHolder.getContext().getAuthentication().getName();

        User fromUser = userRepository.findByCpf(fromCpf);
        User toUser = userRepository.findByCpf(transferMoneyRequest.getToCpf());

        if(fromUser == null || toUser == null) throw new UsernameNotFoundException("Algum dos usuários fornecidos não existe");
        else if(fromUser.getAmount().compareTo(transferMoneyRequest.getAmount()) < 0) throw new InsufficientFundsException();

        BigDecimal amount = transferMoneyRequest.getAmount();

        return this.transferMoney(fromUser, toUser, amount);
    }

    private Transaction transferMoney(User fromUser, User toUser, BigDecimal amount) {
        fromUser.setAmount(fromUser.getAmount().subtract(amount));
        toUser.setAmount(toUser.getAmount().add(amount));
        return this.registerTransaction(fromUser, toUser, amount);
    }

    private Transaction registerTransaction(User fromUser, User toUser, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setFromUser(fromUser);
        transaction.setToUser(toUser);
        transaction.setAmount(amount);
        return this.transactionRepository.save(transaction);
    }
}
