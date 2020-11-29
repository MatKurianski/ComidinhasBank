package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.request.TransferMoneyRequest;
import com.kurianski.comidinhasbank.model.view.UserView;
import com.kurianski.comidinhasbank.service.BankStatementService;
import com.kurianski.comidinhasbank.service.TransferMoneyService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/bank-statement")
public class BankStatementController {

    @Autowired
    private BankStatementService bankStatementService;

    @GetMapping
    @JsonView(UserView.Simple.class)
    public ResponseEntity getBankStatement(@RequestBody @NonNull Integer days) {
        List<Transaction> transactionsHistory = bankStatementService.getUserExtract(days);
        return ResponseEntity.ok(transactionsHistory);
    }
}
