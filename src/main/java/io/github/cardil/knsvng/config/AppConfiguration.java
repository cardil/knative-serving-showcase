package io.github.cardil.knsvng.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.wavesoftware.eid.utils.EidExecutions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import java.net.URI;
import java.util.Optional;

@ApplicationScoped
class AppConfiguration implements
  EventsConfiguration, GreetConfiguration, DelayConfiguration {

  private static final String UNSET_BROKER_ADDRESS = "http://localhost:31111";
  private static final String SINK_ADDRESS = "k.sink";

  private final Provider<String> sinkAddress;
  private final Provider<String> greet;
  private final Provider<Long> delay;

  @Inject
  AppConfiguration(
    @ConfigProperty(name = SINK_ADDRESS, defaultValue = UNSET_BROKER_ADDRESS)
      Provider<String> sinkAddress,
    @ConfigProperty(name = "greet", defaultValue = "Hello")
      Provider<String> greet,
    @ConfigProperty(name = "delay", defaultValue = "0")
      Provider<Long> delay
  ) {
    this.sinkAddress = sinkAddress;
    this.greet = greet;
    this.delay = delay;
  }

  @Override
  public URI sinkAddress() {
    return EidExecutions.tryToExecute(() -> new URI(sinkAddress.get()), "20200302:153319");
  }

  @Override
  public String greeting() {
    return greet.get();
  }

  @Override
  public Optional<Long> delayInMillis() {
    var delayAsLong = delay.get();
    if (delayAsLong > 0L) {
      return Optional.of(delayAsLong);
    }
    return Optional.empty();
  }

}
