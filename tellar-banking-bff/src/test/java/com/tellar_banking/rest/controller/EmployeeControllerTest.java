package com.tellar_banking.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tellar_banking.data.entity.Company;
import com.tellar_banking.data.entity.CreditAccount;
import com.tellar_banking.data.entity.Employee;
import com.tellar_banking.exception.EmployeeAlreadyExistsException;
import com.tellar_banking.rest.controller.handler.EmployeeRequest;
import com.tellar_banking.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterEmployeeSuccess() throws Exception {
        var request = new EmployeeRequest();
        var empName = "Test Employee";
        var email = "test@company.com";
        var companyName = "Company1";
        request.setEmail(email);
        request.setCompany(companyName);
        request.setName(empName);

        var employee = new Employee();
        var company = new Company();
        var creditAccount = new CreditAccount();
        creditAccount.setCreditBalance(BigDecimal.valueOf(1000.00));
        employee.setEmail(email);
        company.setName(companyName);
        employee.setCompany(company);
        employee.setName(empName);
        employee.setCreditAccount(creditAccount);




        Mockito.when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(employee);

        mockMvc.perform(post("/teller-banking/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.credit").value(1000.00));
    }

    @Test
    void testRegisterEmployeeAlreadyExists() throws Exception {
        var request = new EmployeeRequest();
        var empName = "Test Employee";
        request.setEmail("test@company.com");
        request.setCompany("Company1");
        request.setName(empName);

        Mockito.doThrow(new EmployeeAlreadyExistsException()).when(employeeService).createEmployee(any(EmployeeRequest.class));

        mockMvc.perform(post("/teller-banking/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void testGetEmployeeBalanceSuccess() throws Exception {
        var request = new EmployeeRequest();
        var empName = "Test Employee";
        var email = "test@company.com";
        var companyName = "Company1";
        request.setEmail(email);
        request.setCompany(companyName);
        request.setName(empName);

        Mockito.when(employeeService.getEmployeeCredit(email, companyName)).thenReturn(BigDecimal.valueOf(10000.00));

        mockMvc.perform(post("/teller-banking/api/v1/employees/credit-balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.credit").value(10000.00));
    }

}
