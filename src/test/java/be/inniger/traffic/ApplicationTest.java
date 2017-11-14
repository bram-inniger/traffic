package be.inniger.traffic;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void stateChangesCyclically() throws Exception {
    mvc.perform(get("/turn"))
        .andExpect(jsonPath("$.from", is(LightsState.RED.name())))
        .andExpect(jsonPath("$.to", is(LightsState.GREEN.name())));

    mvc.perform(get("/turn"))
        .andExpect(jsonPath("$.from", is(LightsState.GREEN.name())))
        .andExpect(jsonPath("$.to", is(LightsState.AMBER.name())));

    mvc.perform(get("/turn"))
        .andExpect(jsonPath("$.from", is(LightsState.AMBER.name())))
        .andExpect(jsonPath("$.to", is(LightsState.RED.name())));

    mvc.perform(get("/turn"))
        .andExpect(jsonPath("$.from", is(LightsState.RED.name())))
        .andExpect(jsonPath("$.to", is(LightsState.GREEN.name())));
  }
}
