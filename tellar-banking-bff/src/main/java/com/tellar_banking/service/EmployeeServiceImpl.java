package com.tellar_banking.service;

import com.tellar_banking.data.entity.Company;
import com.tellar_banking.data.entity.CreditAccount;
import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.data.repository.CompanyRepository;
import com.tellar_banking.data.repository.CreditAccountRepository;
import com.tellar_banking.data.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Employee registerEmployee(String email, String name, String companyName) {
        Company company = companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        CreditAccount creditAccount = new CreditAccount();
        creditAccount.setCreditBalance(BigDecimal.valueOf(1000));
        creditAccountRepository.save(creditAccount);

        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setName(name);
        employee.setCompany(company);
        employee.setCreditAccount(creditAccount);

        return employeeRepository.save(employee);
    }

    @Override
    public BigDecimal getEmployeeCreditBalance(String email, String companyName) {
        Employee employee = employeeRepository.findByEmailAndCompany_Name(email, companyName)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.getCreditAccount().getCreditBalance();
    }

    @Override
    public List<Employee> listEmployees(String companyName) {
        return employeeRepository.findByCompany_Name(companyName);
    }

    @Override
    public Employee updateCreditBalance(String email, String companyName, BigDecimal newBalance) {
        Employee employee = employeeRepository.findByEmailAndCompany_Name(email, companyName)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        CreditAccount creditAccount = employee.getCreditAccount();
        creditAccount.setCreditBalance(newBalance);
        creditAccountRepository.save(creditAccount);

        return employee;
    }
}
