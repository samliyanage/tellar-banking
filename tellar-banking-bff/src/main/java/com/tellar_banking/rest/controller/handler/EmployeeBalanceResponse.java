package com.tellar_banking.rest.controller.handler;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeBalanceResponse {
    private BigDecimal credit;

    public EmployeeBalanceResponse(BigDecimal credit) {
        this.credit = credit;
    }
}
