package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.repository.TransactionRepository;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class BankStatementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getUserExtract(int days) {
        if(days <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Intervalo de dias fornecido é inválido");

        String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByCpf(cpf);

        if(user == null) throw new UsernameNotFoundException("Esse usuário é inválido");

        return transactionRepository.getAllByFromUserOrToUserAndCreatedAtAfterOrderByCreatedAtDesc(user, user, java.sql.Date.valueOf(LocalDate.now().minusDays(days)));
    }
}
