package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.model.view.UserView;
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
    @JsonView(UserView.Detailed.class)
    public ResponseEntity transferMoneyWithCpf(@RequestBody @NonNull @Valid TransferMoneyRequest transferMoneyRequest) {
        Transaction result = transferMoneyService.transferMoneyWithCpf(transferMoneyRequest);
        return ResponseEntity.ok(result);
    }
}
