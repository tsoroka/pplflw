package com.example.pplflwapi.statemachine.action;

import com.example.pplflwapi.domain.Employee;
import com.example.pplflwapi.repository.EmployeeRepository;
import com.example.pplflwapi.statemachine.event.EmployeeEvent;
import com.example.pplflwapi.statemachine.state.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class UpdateEmployeeStateAction implements Action<EmployeeState, EmployeeEvent> {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void execute(StateContext<EmployeeState, EmployeeEvent> context) {
        Long employeeId = context.getExtendedState().get("EMPLOYEE_ID", Long.class);
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        employee.ifPresent(e -> {
            e.setState(context.getTarget().getId());

            log.info("Saving employee state: {}", employee);
            employeeRepository.save(e);
        });
    }
}
