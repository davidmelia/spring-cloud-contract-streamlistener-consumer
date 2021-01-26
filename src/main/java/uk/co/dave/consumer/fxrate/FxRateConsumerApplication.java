package uk.co.dave.consumer.fxrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableSchemaRegistryClient
@EnableBinding(FxRateChannels.class)
public class FxRateConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(FxRateConsumerApplication.class, args);
  }

}
