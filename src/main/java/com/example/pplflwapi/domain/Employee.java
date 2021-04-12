package com.example.pplflwapi.domain;

import com.example.pplflwapi.state.EmployeeState;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Employee {

    @NotEmpty
    private String name;

    @NotNull
    private Integer age;

    private EmployeeState state;

}
