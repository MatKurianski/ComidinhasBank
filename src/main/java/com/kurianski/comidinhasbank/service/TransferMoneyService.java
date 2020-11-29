package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.exception.InsufficientFundsException;
import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.repository.TransactionRepository;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Transaction transferMoneyWithCpf(String fromCpf, TransferMoneyRequest transferMoneyRequest) {
        User fromUser = userRepository.getUserByCpf(fromCpf);
        User toUser = userRepository.getUserByCpf(transferMoneyRequest.getToCpf());

        if(fromUser.getAmount().compareTo(transferMoneyRequest.getAmount()) < 0) throw new InsufficientFundsException();

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
        return transactionRepository.save(transaction);
    }
}
