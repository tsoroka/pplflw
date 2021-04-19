package com.example.pplflwapi.configuration;

import com.example.pplflwapi.repository.EmployeeRepository;
import com.example.pplflwapi.statemachine.action.UpdateEmployeeStateAction;
import com.example.pplflwapi.statemachine.event.EmployeeEvent;
import com.example.pplflwapi.statemachine.state.EmployeeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

import static com.example.pplflwapi.statemachine.event.EmployeeEvent.APPROVE;
import static com.example.pplflwapi.statemachine.event.EmployeeEvent.CHECK;
import static com.example.pplflwapi.statemachine.event.EmployeeEvent.MAKE_ACTIVE;
import static com.example.pplflwapi.statemachine.state.EmployeeState.ACTIVE;
import static com.example.pplflwapi.statemachine.state.EmployeeState.ADDED;
import static com.example.pplflwapi.statemachine.state.EmployeeState.APPROVED;
import static com.example.pplflwapi.statemachine.state.EmployeeState.IN_CHECK;

@Slf4j
@Configuration
@EnableStateMachineFactory
public class PplflwStateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

    @Autowired
    private JpaStateMachineRepository jpaStateMachineRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

    @Override
    public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
        states
                .withStates()
                .initial(ADDED)
                .end(ACTIVE)
                .states(EnumSet.allOf(EmployeeState.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener())
                .and()
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister(jpaStateMachineRepository));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(ADDED)
                .target(IN_CHECK)
                .event(CHECK)
                .action(processEmployeeStateUpdateAction())

                .and()
                .withExternal()
                .source(IN_CHECK)
                .target(APPROVED)
                .event(APPROVE)
                .action(processEmployeeStateUpdateAction())

                .and()
                .withExternal()
                .source(APPROVED)
                .target(ACTIVE)
                .event(MAKE_ACTIVE)
                .action(processEmployeeStateUpdateAction());
    }

    @Bean
    public Action<EmployeeState, EmployeeEvent> processEmployeeStateUpdateAction() {
        return new UpdateEmployeeStateAction(employeeRepository);
    }

    public StateMachineListener<EmployeeState, EmployeeEvent> listener() {
        return new StateMachineListenerAdapter<>() {

            @Override
            public void stateChanged(State<EmployeeState, EmployeeEvent> from, State<EmployeeState, EmployeeEvent> to) {
                log.info("State changed to {}", to.getId());
            }

            @Override
            public void eventNotAccepted(Message<EmployeeEvent> event) {
                log.error("Event not accepted: {}", event.getPayload());
            }

            @Override
            public void stateMachineStarted(StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
                log.debug("Machine started");
            }

            @Override
            public void stateMachineStopped(StateMachine<EmployeeState, EmployeeEvent> stateMachine) {
                log.debug("Machine stopped");
            }
        };
    }

    @Bean
    public StateMachineRuntimePersister<EmployeeState, EmployeeEvent, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Bean
    public StateMachineService<EmployeeState, EmployeeEvent> stateMachineService() {
        return new DefaultStateMachineService<>(stateMachineFactory,
                stateMachineRuntimePersister(jpaStateMachineRepository));
    }
}
