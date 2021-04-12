package com.example.pplflwapi.controller;

import com.example.pplflwapi.domain.Employee;
import com.example.pplflwapi.event.EmployeeEvent;
import com.example.pplflwapi.state.EmployeeState;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employee create(Employee employee) {

        return new Employee();
    }

    @PutMapping(path = "/{employeeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Employee update(@PathVariable String employeeId,
                           @RequestBody @Valid @NotNull Employee employee) {

        return new Employee();
    }

    @PatchMapping(path = "/{employeeId}/events/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void triggerEvent(@PathVariable String employeeId, @PathVariable EmployeeEvent eventId) {

    }
}
