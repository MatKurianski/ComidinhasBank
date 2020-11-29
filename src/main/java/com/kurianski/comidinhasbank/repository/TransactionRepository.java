package com.kurianski.comidinhasbank.repository;

import com.kurianski.comidinhasbank.model.Transaction;
import com.kurianski.comidinhasbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> getAllByFromUserOrToUserAndCreatedAtAfterOrderByCreatedAtDesc(@NotNull User fromUser, @NotNull User toUser, @NotNull Date createdAt);
}
