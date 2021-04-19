package com.example.pplflwapi.service;

import com.example.pplflwapi.domain.Employee;
import com.example.pplflwapi.statemachine.event.EmployeeEvent;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface EmployeeService {

    public List<Employee> findAll();

    public Employee find(@NotNull Long employeeId);

    public Employee create(@NotNull @Valid Employee employee);

    public Employee update(@NotNull @Valid Employee employee);

    public void processEvent(@NotNull Long employeeId, @NotNull EmployeeEvent employeeEvent);
}
