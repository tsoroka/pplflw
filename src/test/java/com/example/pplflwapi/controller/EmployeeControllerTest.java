package com.example.pplflwapi.controller;

import com.example.pplflwapi.controller.model.EmployeeState;
import com.example.pplflwapi.controller.model.request.EmployeeSaveRequest;
import com.example.pplflwapi.controller.model.response.EmployeeResponse;
import com.example.pplflwapi.statemachine.event.EmployeeEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EmployeeControllerTest {

    private static final int EMPLOYEE_AGE = 11;
    private static final String EMPLOYEE_NAME = "Test";

    @Autowired
    private EmployeeController employeeController;

    @Test
    void createAndGetEmployeeTest() {
        EmployeeSaveRequest employeeSaveRequest = new EmployeeSaveRequest();
        employeeSaveRequest.setAge(EMPLOYEE_AGE);
        employeeSaveRequest.setName(EMPLOYEE_NAME);

        EmployeeResponse savedEmployeeResponse = employeeController.create(employeeSaveRequest);
        EmployeeResponse employeeResponse = employeeController.find(savedEmployeeResponse.getId());

        assertEquals(EMPLOYEE_AGE, employeeResponse.getAge());
        assertEquals(EMPLOYEE_NAME, employeeResponse.getName());
        assertEquals(EmployeeState.ADDED, employeeResponse.getState());
    }

    @Test
    void createAndCheckEmployeeTest() {
        EmployeeSaveRequest employeeSaveRequest = new EmployeeSaveRequest();
        employeeSaveRequest.setAge(EMPLOYEE_AGE);
        employeeSaveRequest.setName(EMPLOYEE_NAME);

        EmployeeResponse savedEmployeeResponse = employeeController.create(employeeSaveRequest);
        EmployeeResponse employeeResponse = employeeController.find(savedEmployeeResponse.getId());

        employeeResponse = employeeController.processEvent(employeeResponse.getId(), EmployeeEvent.CHECK);

        assertEquals(EMPLOYEE_AGE, employeeResponse.getAge());
        assertEquals(EMPLOYEE_NAME, employeeResponse.getName());
        assertEquals(EmployeeState.IN_CHECK, employeeResponse.getState());
    }

    @Test
    void createAndApproveEmployeeTest() {
        EmployeeSaveRequest employeeSaveRequest = new EmployeeSaveRequest();
        employeeSaveRequest.setAge(EMPLOYEE_AGE);
        employeeSaveRequest.setName(EMPLOYEE_NAME);

        EmployeeResponse savedEmployeeResponse = employeeController.create(employeeSaveRequest);
        EmployeeResponse employeeResponse = employeeController.find(savedEmployeeResponse.getId());

        employeeController.processEvent(employeeResponse.getId(), EmployeeEvent.CHECK);
        employeeResponse = employeeController.processEvent(employeeResponse.getId(), EmployeeEvent.APPROVE);

        assertEquals(EMPLOYEE_AGE, employeeResponse.getAge());
        assertEquals(EMPLOYEE_NAME, employeeResponse.getName());
        assertEquals(EmployeeState.APPROVED, employeeResponse.getState());
    }

    @Test
    void createAndActivateEmployeeTest() {
        EmployeeSaveRequest employeeSaveRequest = new EmployeeSaveRequest();
        employeeSaveRequest.setAge(EMPLOYEE_AGE);
        employeeSaveRequest.setName(EMPLOYEE_NAME);

        EmployeeResponse savedEmployeeResponse = employeeController.create(employeeSaveRequest);
        EmployeeResponse employeeResponse = employeeController.find(savedEmployeeResponse.getId());

        employeeController.processEvent(employeeResponse.getId(), EmployeeEvent.CHECK);
        employeeController.processEvent(employeeResponse.getId(), EmployeeEvent.APPROVE);
        employeeResponse = employeeController.processEvent(employeeResponse.getId(), EmployeeEvent.MAKE_ACTIVE);

        assertEquals(EMPLOYEE_AGE, employeeResponse.getAge());
        assertEquals(EMPLOYEE_NAME, employeeResponse.getName());
        assertEquals(EmployeeState.ACTIVE, employeeResponse.getState());
    }
}