package com.example.payroll;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.payroll.Employee;

interface EmployeeRepository extends JpaRepository<Employee, Long> {

}