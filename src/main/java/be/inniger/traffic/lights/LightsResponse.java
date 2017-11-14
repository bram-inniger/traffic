package be.inniger.traffic.lights;

public class LightsResponse {

  private final LightsState from;
  private final LightsState to;

  @SuppressWarnings("WeakerAccess")
  public LightsResponse(LightsState from, LightsState to) {
    this.from = from;
    this.to = to;
  }

  @SuppressWarnings("unused")
  public LightsState getFrom() {
    return from;
  }

  @SuppressWarnings("unused")
  public LightsState getTo() {
    return to;
  }
}
