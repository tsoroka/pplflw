package com.example.pplflwapi.controller.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeSaveRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Integer age;
}
