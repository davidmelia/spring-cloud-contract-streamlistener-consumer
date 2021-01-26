package uk.co.dave;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.dave.consumer.fxrate.FxRateChannels;
import uk.co.dave.consumer.fxrate.FxRateConsumerApplication;
import uk.co.dave.consumer.fxrate.consumer.FxRateConsumer;
import uk.co.dave.consumer.fxrate.consumer.avro.AvroFxRateEvent;
import uk.co.dave.consumer.fxrate.consumer.json.JsonFxRateEvent;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FxRateConsumerApplication.class}, webEnvironment = WebEnvironment.NONE)
@AutoConfigureStubRunner(stubsMode = StubsMode.CLASSPATH, ids = {"uk.co.dave:fx-producer:+:stubs"})
public class ConsumerIntegartionTest {
  @Autowired
  private StubTrigger stubTrigger;

  @Autowired
  private FxRateConsumer fxRateConsumer;
  
  @Autowired
  private FxRateChannels fxRateChannels;


  @Test
  public void testJsonFxRateEvent() {
    JsonFxRateEvent expected = new JsonFxRateEvent("GBP", "USD", BigDecimal.TEN);
    fxRateChannels.consumeJsonIn().send(MessageBuilder.withPayload(expected).build());
    Assertions.assertEquals(expected, fxRateConsumer.getLastJsonFxRateEvent());
  }
  
  @Test
  public void testJsonFxRateEventViaSpringCloudContract() {
    JsonFxRateEvent expected = new JsonFxRateEvent("GBP", "USD", BigDecimal.valueOf(1.23));
    stubTrigger.trigger("triggerJsonFxRateEvent");
    Assertions.assertEquals(expected, fxRateConsumer.getLastJsonFxRateEvent());
  }
  
  @Test
  public void testAvroFxRateEvent() {
    AvroFxRateEvent expected = AvroFxRateEvent.newBuilder().setFrom("GBP").setTo("USD").setRate(BigDecimal.TEN).build();
    fxRateChannels.consumeAvroIn().send(MessageBuilder.withPayload(expected).build());
    Assertions.assertEquals(expected, fxRateConsumer.getLastAvroFxRateEvent());
  }

  @Test
  public void testAvroFxRateEventViaSpringCloudContract() {
    AvroFxRateEvent expected = AvroFxRateEvent.newBuilder().setFrom("GBP").setTo("USD").setRate(BigDecimal.valueOf(1.23)).build();
    stubTrigger.trigger("triggerAvroFxRateEvent");
    Assertions.assertEquals(expected, fxRateConsumer.getLastAvroFxRateEvent());
  }

}
