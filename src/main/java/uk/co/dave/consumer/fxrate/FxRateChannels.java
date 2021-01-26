package uk.co.dave.consumer.fxrate;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface FxRateChannels {
  String CONSUME_JSON_IN = "consumeJson-in-0";

  @Input(CONSUME_JSON_IN)
  SubscribableChannel consumeJsonIn();

  String CONSUME_AVRO_IN = "consumeAvro-in-0";

  @Input(CONSUME_AVRO_IN)
  SubscribableChannel consumeAvroIn();
}
