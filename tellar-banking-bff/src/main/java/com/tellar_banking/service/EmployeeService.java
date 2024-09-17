package com.tellar_banking.service;

import com.tellar_banking.data.entity.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    Employee registerEmployee(String email, String name, String companyName);

    BigDecimal getEmployeeCreditBalance(String email, String companyName);

    List<Employee> listEmployees(String companyName);

    Employee updateCreditBalance(String email, String companyName, BigDecimal newBalance);
}
