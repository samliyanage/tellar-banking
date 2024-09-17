package com.tellar_banking.rest.controller.handler;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeRequest {
    private String email;
    private String name;
    private String company;
    private BigDecimal newBalance;

    public EmployeeRequest(String email, String name, String company, BigDecimal newBalance) {
        this.email = email;
        this.name = name;
        this.company = company;
        this.newBalance = newBalance;
    }
}

