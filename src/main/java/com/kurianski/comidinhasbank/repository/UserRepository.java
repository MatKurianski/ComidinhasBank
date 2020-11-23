package com.kurianski.comidinhasbank.repository;

import com.kurianski.comidinhasbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByCpf(String cpf);
}
