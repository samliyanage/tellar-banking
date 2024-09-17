package com.tellar_banking.service;

import com.tellar_banking.data.entity.CreditAccount;
import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.data.repository.CompanyRepository;
import com.tellar_banking.data.repository.CreditAccountRepository;
import com.tellar_banking.data.repository.EmployeeRepository;
import com.tellar_banking.exception.CompanyNotFoundException;
import com.tellar_banking.exception.EmployeeAlreadyExistsException;
import com.tellar_banking.exception.EmployeeNotFoundException;
import com.tellar_banking.rest.controller.handler.EmployeeRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Value("${tellar.credit-balance.default-amount}")
    private BigDecimal defaultCredit;

    @Transactional
    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        if (employeeRepository.findByEmailAndCompanyName(employeeRequest.getEmail(), employeeRequest.getCompany()).isPresent()) {
            throw new EmployeeAlreadyExistsException();
        }

        var company = companyRepository.findByName(employeeRequest.getCompany())
                .orElseThrow(CompanyNotFoundException::new);

        var creditAccount = new CreditAccount();
        creditAccount.setCreditBalance(defaultCredit);
        creditAccount = creditAccountRepository.save(creditAccount);

        var employee = new Employee();
        employee.setEmail(employeeRequest.getEmail());
        employee.setName(employeeRequest.getName());
        employee.setCompany(company);
        employee.setCreditAccount(creditAccount);

        return employeeRepository.save(employee);
    }

    @Override
    public BigDecimal getEmployeeCredit(String email, String companyName) {
        var employee = employeeRepository.findByEmailAndCompanyName(email, companyName)
                .orElseThrow(EmployeeNotFoundException::new);
        return employee.getCreditAccount().getCreditBalance();
    }

    @Override
    public List<Employee> getAllEmployeesForCompany(String companyName) {
        var company = companyRepository.findByName(companyName)
                .orElseThrow(CompanyNotFoundException::new);
        return employeeRepository.findAllByCompanyName(companyName);
    }

    @Transactional
    @Override
    public Employee updateEmployeeCredit(String email, String companyName, BigDecimal newCredit) {
        Employee employee = employeeRepository.findByEmailAndCompanyName(email, companyName)
                .orElseThrow(EmployeeNotFoundException::new);

        CreditAccount creditAccount = employee.getCreditAccount();
        creditAccount.setCreditBalance(newCredit);
        creditAccountRepository.save(creditAccount);

        return employee;
    }
}


