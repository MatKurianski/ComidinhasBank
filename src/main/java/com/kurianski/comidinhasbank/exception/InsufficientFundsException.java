package com.kurianski.comidinhasbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Saldo insuficiente")
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Saldo insuficiente");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
