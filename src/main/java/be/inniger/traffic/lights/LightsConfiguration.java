package be.inniger.traffic.lights;

import java.util.EnumSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
@EnableStateMachine
public class LightsConfiguration extends EnumStateMachineConfigurerAdapter<LightsState, LightsEvent> {

  private static final Log log = LogFactory.getLog(LightsConfiguration.class);

  @Override
  public void configure(StateMachineConfigurationConfigurer<LightsState, LightsEvent> config) throws Exception {
    config
        .withConfiguration()
        .autoStartup(true)
        .listener(listener());
  }

  @Override
  public void configure(StateMachineStateConfigurer<LightsState, LightsEvent> states) throws Exception {
    states
        .withStates()
        .initial(LightsState.RED)
        .states(EnumSet.allOf(LightsState.class));
  }

  @Override
  public void configure(StateMachineTransitionConfigurer<LightsState, LightsEvent> transitions) throws Exception {
    transitions
        .withExternal()
        .source(LightsState.GREEN).target(LightsState.AMBER).event(LightsEvent.TURN)
        .and()
        .withExternal()
        .source(LightsState.AMBER).target(LightsState.RED).event(LightsEvent.TURN)
        .and()
        .withExternal()
        .source(LightsState.RED).target(LightsState.GREEN).event(LightsEvent.TURN);
  }

  @Bean
  public StateMachineListener<LightsState, LightsEvent> listener() {
    return new StateMachineListenerAdapter<LightsState, LightsEvent>() {

      @Override
      public void stateChanged(State<LightsState, LightsEvent> from, State<LightsState, LightsEvent> to) {
        log.info("State change to " + to.getId());
      }
    };
  }
}
