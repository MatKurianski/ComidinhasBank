package com.kurianski.comidinhasbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kurianski.comidinhasbank.model.view.UserView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fromCpf", referencedColumnName = "cpf")
    @NotNull
    @JsonView(UserView.Simple.class)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "toCpf", referencedColumnName = "cpf")
    @NotNull
    @JsonView(UserView.Simple.class)
    private User toUser;

    @CreationTimestamp
    private Date createdAt;

    @Column(precision = 10, scale = 2, nullable = false)
    @JsonView(UserView.Simple.class)
    private BigDecimal amount;
}
