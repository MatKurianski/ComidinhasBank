package com.kurianski.comidinhasbank.model.request;

import com.kurianski.comidinhasbank.model.enumerables.Gender;
import com.kurianski.comidinhasbank.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class UserCreationRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @CPF
    @NotBlank
    private String cpf;

    @Email
    @NotBlank
    private String email;

    @ValidPassword
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
