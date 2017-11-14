package be.inniger.traffic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LightsController {

  private final StateMachine<LightsState, LightsEvent> stateMachine;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  public LightsController(StateMachine<LightsState, LightsEvent> stateMachine) {
    this.stateMachine = stateMachine;
  }

  // FIXME - Mutating state via GET is evil
  @GetMapping("turn")
  public LightsResponse turn() {
    LightsState previousState = stateMachine.getState().getId();
    stateMachine.sendEvent(LightsEvent.TURN);
    LightsState currentState = stateMachine.getState().getId();

    return new LightsResponse(previousState, currentState);
  }
}
