package com.example.pplflwapi.controller;

import com.example.pplflwapi.controller.model.request.EmployeeSaveRequest;
import com.example.pplflwapi.controller.model.response.EmployeeResponse;
import com.example.pplflwapi.domain.Employee;
import com.example.pplflwapi.mapper.EmployeeMapper;
import com.example.pplflwapi.service.EmployeeService;
import com.example.pplflwapi.statemachine.event.EmployeeEvent;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Retrieves all employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping(path = "/{employeeId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Retrieves employee by id")
    public EmployeeResponse find(@PathVariable Long employeeId) {
        return employeeMapper.toEmployeeResponse(employeeService.find(employeeId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation("Creates new employee")
    public EmployeeResponse create(@RequestBody @NotNull @Valid EmployeeSaveRequest employeeSaveRequest) {
        Employee employee = employeeMapper.toEmployee(employeeSaveRequest);
        return employeeMapper.toEmployeeResponse(employeeService.create(employee));
    }

    @PutMapping(path = "/{employeeId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Updates employee by id")
    public EmployeeResponse update(@PathVariable Long employeeId,
                           @RequestBody @NotNull @Valid EmployeeSaveRequest employeeSaveRequest) {
        Employee employee = employeeMapper.toEmployee(employeeSaveRequest);
        employee.setId(employeeId);
        return employeeMapper.toEmployeeResponse(employeeService.update(employee));
    }

    @PatchMapping(path = "/{employeeId}/events/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation("Processes event by employee id")
    public EmployeeResponse processEvent(@PathVariable Long employeeId, @PathVariable EmployeeEvent eventId) {
        employeeService.processEvent(employeeId, eventId);
        return employeeMapper.toEmployeeResponse(employeeService.find(employeeId));
    }
}
