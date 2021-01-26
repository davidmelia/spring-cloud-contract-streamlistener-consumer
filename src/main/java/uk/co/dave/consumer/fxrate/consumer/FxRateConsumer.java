package uk.co.dave.consumer.fxrate.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import uk.co.dave.consumer.fxrate.FxRateChannels;
import uk.co.dave.consumer.fxrate.consumer.avro.AvroFxRateEvent;
import uk.co.dave.consumer.fxrate.consumer.json.JsonFxRateEvent;


@Slf4j
@Component
public class FxRateConsumer {

  @Getter
  private JsonFxRateEvent lastJsonFxRateEvent;
  @Getter
  private AvroFxRateEvent lastAvroFxRateEvent;

  @StreamListener(FxRateChannels.CONSUME_JSON_IN)
  public void consumeJson(final JsonFxRateEvent jsonFxRateEvent) {
    log.info("jsonFxRateEvent = {}", jsonFxRateEvent);
    this.lastJsonFxRateEvent = jsonFxRateEvent;
  }
  
  @StreamListener(FxRateChannels.CONSUME_AVRO_IN)
  public void consumeAvro(final AvroFxRateEvent avroFxRateEvent) {
    log.info("avroFxRateEvent = {}", avroFxRateEvent);
    this.lastAvroFxRateEvent = avroFxRateEvent;
  }

}
