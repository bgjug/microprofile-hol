import bg.jug.magman.subscribers.domain.Subscriber;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Dmitry Alexandrov on 25.10.16.
 */
public class TestSubscribers {

    private final Logger log = Logger.getLogger(TestSubscribers.class.getName());

    @Test
    public void readSubscribers() throws Exception {
        final ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        final InputStream is = this.getClass().getResourceAsStream("/subscribers.json");
        final Set<Subscriber> subscribers = om.readValue(is, new TypeReference<Set<Subscriber>>() {
        });

        Assert.assertFalse("Failed to get any Subscribers", subscribers.isEmpty());

        for (final Subscriber subscriber : subscribers) {
            Assert.assertNotNull(subscriber.getSubscribedUntil());
            this.log.info(String.valueOf(subscriber.getSubscribedUntil()));
        }
    }
}
