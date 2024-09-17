package com.tellar_banking.rest.controller.handler;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateCreditRequest {
    @NotEmpty
    private String email;
    @NotEmpty
    private String company;
    @NotNull
    private BigDecimal newCredit;
}
