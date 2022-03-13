import events.DateTimeEvent;
import events.RandomUUIDEvent;
import handlers.DateTimeEventHandler1;
import handlers.RandomUUIDEventHandler;
import com.aureum.springboot.config.SpringBootMediatorAutoConfiguration;
import com.aureum.springboot.exceptions.UnsupportedEventException;
import com.aureum.springboot.service.Mediator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class EventHandlerTest {

    @Test
    void executeUnsupportedEventWithBean() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .run( context -> {
                    assertThrows(UnsupportedEventException.class,
                            () -> context.getBean(Mediator.class).publish(new RandomUUIDEvent()));
                });
    }

    @Test
    void executeUnsupportedEventWithNoBean() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .run( context -> {
                    assertThrows(UnsupportedEventException.class,
                            () -> context.getBean(Mediator.class).publish(new RandomUUIDEvent()));
                });
    }

    @Test
    void executeSingleHandler() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .run( context -> {
                    context.getBean(Mediator.class).publish(new DateTimeEvent());
                });
    }

    @Test
    void executeMultipleHandler() {
        final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

        contextRunner.withUserConfiguration(SpringBootMediatorAutoConfiguration.class)
                .withBean(DateTimeEventHandler1.class)
                .withBean(RandomUUIDEventHandler.class)
                .run( context -> {
                    context.getBean(Mediator.class).publish(new RandomUUIDEvent());
                });
    }
}