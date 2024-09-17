package com.tellar_banking.rest.controller.handler;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeResponse {
    private String name;
    private BigDecimal credit;

    public EmployeeResponse(String name, BigDecimal credit) {
        this.name = name;
        this.credit = credit;
    }
}
