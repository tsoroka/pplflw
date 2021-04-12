package com.example.pplflwapi.configuration;

import static com.example.pplflwapi.event.EmployeeEvent.*;
import static com.example.pplflwapi.state.EmployeeState.*;
import com.example.pplflwapi.event.EmployeeEvent;
import com.example.pplflwapi.state.EmployeeState;
import java.util.EnumSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@EnableStateMachineFactory
@Configuration
public class PplflwStateMachineConfig extends EnumStateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

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
                .listener();
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(ADDED)
                .target(IN_CHECK)
                .event(CHECK)
                .action()

                .and()
                .withExternal()
                .source(IN_CHECK)
                .target(APPROVED)
                .event(APPROVE)
                .action()

                .and()
                .withExternal()
                .source(APPROVED)
                .target(ACTIVE)
                .event(MAKE_ACTIVE)
                .action()
    }
}
