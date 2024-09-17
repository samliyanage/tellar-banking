package com.tellar_banking.rest.controller;


import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.rest.controller.handler.EmployeeBalanceResponse;
import com.tellar_banking.rest.controller.handler.EmployeeRequest;
import com.tellar_banking.rest.controller.handler.EmployeeResponse;
import com.tellar_banking.rest.controller.handler.UpdateCreditRequest;
import com.tellar_banking.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teller-banking/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> registerEmployee(@RequestBody @Valid EmployeeRequest request) {
        Employee employee = employeeService.createEmployee(request);
        return new ResponseEntity<>(new EmployeeResponse(employee.getName(), employee.getCreditAccount().getCreditBalance()), HttpStatus.CREATED);
    }

    @PostMapping("/credit-balance")
    public ResponseEntity<EmployeeBalanceResponse> getEmployeeCreditBalance(@RequestBody @Valid EmployeeRequest request) {
        BigDecimal credit = employeeService.getEmployeeCredit(request.getEmail(), request.getCompany());
        return new ResponseEntity<>(new EmployeeBalanceResponse(credit), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(@RequestParam("company_name") String companyName) {
        List<Employee> employees = employeeService.getAllEmployeesForCompany(companyName);
        List<EmployeeResponse> responses = employees.stream()
                .map(employee -> new EmployeeResponse(employee.getName(), employee.getCreditAccount().getCreditBalance()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PatchMapping("/credit-balance")
    public ResponseEntity<EmployeeResponse> updateEmployeeCredit(@RequestBody @Valid UpdateCreditRequest request) {
        Employee employee = employeeService.updateEmployeeCredit(request.getEmail(), request.getCompany(), request.getNewCredit());
        return new ResponseEntity<>(new EmployeeResponse(employee.getName(), employee.getCreditAccount().getCreditBalance()), HttpStatus.OK);
    }
}
