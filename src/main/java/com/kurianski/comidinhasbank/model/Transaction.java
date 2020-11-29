package com.kurianski.comidinhasbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "toCpf", referencedColumnName = "cpf")
    @NotNull
    private User toUser;

    @CreationTimestamp
    private Date createdAt;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;
}
