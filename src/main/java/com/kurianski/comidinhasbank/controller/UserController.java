package com.kurianski.comidinhasbank.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.model.view.UserView;
import com.kurianski.comidinhasbank.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static com.kurianski.comidinhasbank.constant.SecurityConstants.SIGN_UP_URL;

@RestController
@RequestMapping(value="/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(SIGN_UP_URL)
    @JsonView(UserView.Detailed.class)
    public ResponseEntity createUser(@RequestBody @NonNull @Valid UserCreationRequest userCreationRequest) {
        try {
            return ResponseEntity.ok(userService.createUser(userCreationRequest));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com esse CPF ou email!", e);
        }
    }
}
