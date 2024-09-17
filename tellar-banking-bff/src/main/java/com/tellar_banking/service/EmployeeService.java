package com.tellar_banking.service;

import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.rest.controller.handler.EmployeeRequest;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeRequest employeeRequest);
    BigDecimal getEmployeeCredit(String email, String companyName);
    List<Employee> getAllEmployeesForCompany(String companyName);
    Employee updateEmployeeCredit(String email, String companyName, BigDecimal newCredit);
}
