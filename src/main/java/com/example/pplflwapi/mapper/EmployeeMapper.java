package com.example.pplflwapi.mapper;

import com.example.pplflwapi.controller.model.request.EmployeeSaveRequest;
import com.example.pplflwapi.controller.model.response.EmployeeResponse;
import com.example.pplflwapi.domain.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeSaveRequest employeeSaveRequest);

    EmployeeResponse toEmployeeResponse(Employee employee);

}
