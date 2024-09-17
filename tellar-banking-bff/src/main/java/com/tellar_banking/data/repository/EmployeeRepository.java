package com.tellar_banking.data.repository;

import com.tellar_banking.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailAndCompanyName(String email, String companyName);
    List<Employee> findAllByCompanyName(String companyName);
}


