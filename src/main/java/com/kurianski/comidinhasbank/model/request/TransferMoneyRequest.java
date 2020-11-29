package com.kurianski.comidinhasbank.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class TransferMoneyRequest {
    @NotNull
    private final String toCpf;

    @DecimalMin(value = "0.0", inclusive = false, message = "Não é possível inserir números negativos")
    @Digits(integer = 10, fraction = 2, message = "Somente até duas casas decimais é permitido")
    private final BigDecimal amount;
}
