package com.tellar_banking.service;

import com.tellar_banking.data.entity.CreditAccount;
import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.data.repository.CompanyRepository;
import com.tellar_banking.data.repository.CreditAccountRepository;
import com.tellar_banking.data.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Override
    public Employee registerEmployee(String email, String name, String companyName) {
        var company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        var creditAccount = new CreditAccount();
        creditAccount.setCreditBalance(BigDecimal.valueOf(1000));
        creditAccountRepository.save(creditAccount);

        var employee = new Employee();
        employee.setEmail(email);
        employee.setName(name);
        employee.setCompany(company);
        employee.setCreditAccount(creditAccount);

        return employeeRepository.save(employee);
    }

    @Override
    public BigDecimal getEmployeeCreditBalance(String email, String companyName) {
        var employee = employeeRepository.findByEmailAndCompanyName(email, companyName)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.getCreditAccount().getCreditBalance();
    }

    @Override
    public List<Employee> listEmployees(String companyName) {
        return employeeRepository.findByCompanyName(companyName);
    }

    @Override
    public Employee updateCreditBalance(String email, String companyName, BigDecimal newBalance) {
        var employee = employeeRepository.findByEmailAndCompanyName(email, companyName)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        var creditAccount = employee.getCreditAccount();
        creditAccount.setCreditBalance(newBalance);
        creditAccountRepository.save(creditAccount);

        return employee;
    }
}
