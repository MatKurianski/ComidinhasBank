package com.kurianski.comidinhasbank.model.request;

import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.validator.ValidPassword;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
public class UserCreationRequest {
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @CPF
    private String cpf;

    @NonNull
    @Email
    private String email;

    @ValidPassword
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
