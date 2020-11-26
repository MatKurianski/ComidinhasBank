package com.kurianski.comidinhasbank.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.request.UserCreationRequest;
import com.kurianski.comidinhasbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest userCreationRequest) {
        userCreationRequest.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));
        User user = new User();
        BeanUtils.copyProperties(userCreationRequest, user);
        return userRepository.save(user);
    }

}
