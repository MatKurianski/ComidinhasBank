package com.kurianski.comidinhasbank.repository;

import com.kurianski.comidinhasbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByCpf(@NotNull String cpf);
}
