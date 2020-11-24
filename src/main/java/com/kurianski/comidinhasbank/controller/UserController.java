package com.kurianski.comidinhasbank.controller;

import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity createUser(@RequestBody @NonNull @Valid UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok(userService.createUser(userCreationRequest));
    }

}
