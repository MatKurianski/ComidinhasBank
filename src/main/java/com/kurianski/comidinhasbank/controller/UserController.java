package com.kurianski.comidinhasbank.controller;

import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody @NonNull UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok(userService.createUser(userCreationRequest));
    }

}
