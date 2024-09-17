package com.tellar_banking.rest.controller;


import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.rest.controller.handler.EmployeeRequest;
import com.tellar_banking.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tellar-banking/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRequest request) {
        Employee employee = employeeService.registerEmployee(request.getEmail(), request.getName(), request.getCompany());
        return ResponseEntity.ok(Map.of("success", true, "credit", employee.getCreditAccount().getCreditBalance()));
    }

    @GetMapping("/credit-balance")
    public ResponseEntity<?> getEmployeeCredit(@RequestParam String email, @RequestParam String company) {
        BigDecimal credit = employeeService.getEmployeeCreditBalance(email, company);
        return ResponseEntity.ok(Map.of("credit", credit));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listEmployees(@RequestParam String company) {
        List<Employee> employees = employeeService.listEmployees(company);
        return ResponseEntity.ok(employees);
    }

    @PatchMapping("/credit-balance")
    public ResponseEntity<?> updateEmployeeCredit(@RequestParam String email, @RequestParam String company, @RequestParam BigDecimal newBalance) {
        Employee employee = employeeService.updateCreditBalance(email, company, newBalance);
        return ResponseEntity.ok(employee);
    }
}

