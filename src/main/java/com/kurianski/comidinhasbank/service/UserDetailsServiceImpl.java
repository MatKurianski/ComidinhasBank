package com.kurianski.comidinhasbank.service;

import com.kurianski.comidinhasbank.model.User;
import com.kurianski.comidinhasbank.model.details.UserDetailsImp;
import com.kurianski.comidinhasbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        User user = userRepository.findByCpf(cpf);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UserDetailsImp(user);
    }
}
