package com.example.pplflwapi.service.impl;

import com.example.pplflwapi.domain.Employee;
import com.example.pplflwapi.exception.EventProcessingException;
import com.example.pplflwapi.repository.EmployeeRepository;
import com.example.pplflwapi.service.EmployeeService;
import com.example.pplflwapi.statemachine.event.EmployeeEvent;
import com.example.pplflwapi.statemachine.state.EmployeeState;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final StateMachineService<EmployeeState, EmployeeEvent> stateMachineService;

    @Override
    @Transactional
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee find(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return Optional.of(employee).get()
                .orElseThrow(() -> new ObjectNotFoundException(employeeId, "EMPLOYEE"));
    }

    @Override
    @Transactional
    public Employee create(Employee employee) {
        employee.setState(EmployeeState.ADDED);
        Employee save = employeeRepository.save(employee);
        return save;
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void processEvent(Long employeeId, EmployeeEvent employeeEvent) {
        Message<EmployeeEvent> message = MessageBuilder.withPayload(employeeEvent).build();

        StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineService.acquireStateMachine(employeeId.toString());
        stateMachine.getExtendedState().getVariables().put("EMPLOYEE_ID", employeeId);

        stateMachine.sendEvent(Mono.just(message))
                .doOnError(e -> {
                    log.error(e.getMessage(), e);
                    throw new EventProcessingException(e.getMessage(), e);
                })
                .subscribe();
    }
}
