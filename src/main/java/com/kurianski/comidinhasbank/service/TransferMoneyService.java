package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.exception.InsufficientFundsException;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransferMoneyService {

    @Autowired
    private UserRepository userRepository;

    public void transferMoneyWithCpf(String fromCpf, TransferMoneyRequest transferMoneyRequest) {
        User fromUser = userRepository.getUserByCpf(fromCpf);
        User toUser = userRepository.getUserByCpf(transferMoneyRequest.getToCpf());

        if(fromUser.getAmount().compareTo(transferMoneyRequest.getAmount()) < 0) throw new InsufficientFundsException();

        fromUser.setAmount(fromUser.getAmount().subtract(transferMoneyRequest.getAmount()));
        toUser.setAmount(toUser.getAmount().add(transferMoneyRequest.getAmount()));
    }
}
