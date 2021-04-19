package com.example.pplflwapi.controller.model.response;

import com.example.pplflwapi.controller.model.EmployeeState;
import lombok.Data;

@Data
public class EmployeeResponse {

    private Long id;

    private String name;

    private Integer age;

    private EmployeeState state;

}
