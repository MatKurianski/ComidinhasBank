package com.kurianski.comidinhasbank.controller;

import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.service.TransferMoneyService;
import com.kurianski.comidinhasbank.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/transfer")
public class TransferMoneyController {

    @Autowired
    private TransferMoneyService transferMoneyService;

    @PostMapping("/cpf")
    public ResponseEntity transferMoneyWithCpf(@RequestBody @NonNull @Valid TransferMoneyRequest transferMoneyRequest) {
        String fromCpf = SecurityContextHolder.getContext().getAuthentication().getName();
        transferMoneyService.transferMoneyWithCpf(fromCpf, transferMoneyRequest);
        return ResponseEntity.status(200).body("Feito!");
    }
}
