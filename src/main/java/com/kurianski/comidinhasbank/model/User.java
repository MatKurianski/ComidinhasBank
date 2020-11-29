package com.kurianski.comidinhasbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.model.view.UserView;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "user_person")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(UserView.Detailed.class)
    private long bankAccountNumber;

    @Column(nullable = false)
    @NotBlank
    @JsonView(UserView.Simple.class)
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    @JsonView(UserView.Simple.class)
    private String lastName;

    @CPF
    @Column(nullable = false, unique = true)
    @NotBlank
    @JsonView(UserView.Detailed.class)
    private String cpf;

    @Email
    @Column(nullable = false, unique = true)
    @NotBlank
    @JsonView(UserView.Confidential.class)
    private String email;

    @Column(nullable = false)
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonView(UserView.Confidential.class)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonView(UserView.Confidential.class)
    private Gender gender;

    @Column(columnDefinition = "float DEFAULT 0", precision = 10, scale = 2)
    @JsonIgnore
    @JsonView(UserView.Confidential.class)
    private BigDecimal amount = BigDecimal.ZERO;

    @CreationTimestamp
    @JsonView(UserView.Confidential.class)
    private Date createdAt;

    @UpdateTimestamp
    @JsonView(UserView.Confidential.class)
    private Date updatedAt;
}
