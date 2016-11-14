import bg.jug.magman.subscribers.domain.Subscriber;
import bg.jug.magman.subscribers.persistence.SubscribersDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Dmitry Alexandrov on 24.10.16.
 */

@RunWith(Arquillian.class)
@Ignore //to be fixed
public class ResourceSubscribersTest {

    private final Logger log = Logger.getLogger(ResourceSubscribersTest.class.getName());

    @Inject
    private SubscribersDAO subscribersDAO;

    @Deployment(testable = false)
    public static WebArchive deploy() throws Exception {
        return ShrinkWrap.create(WebArchive.class
                , ResourceSubscribersTest.class.getName() + ".war")
                .addPackage("bg.jug.magman.subscribers.domain")
                .addPackage("bg.jug.magman.subscribers.persistence")
                .addPackage("bg.jug.magman.subscribers.rest");
    }

    //@ArquillianResource URL url;

    @Test
    @RunAsClient
    public void testGet() {
        final Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("/");
        final Set<Subscriber> subscribers = target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Set<Subscriber>>() {
                });

        Assert.assertTrue(subscribers.size() == 2);
        subscribers.forEach(e -> this.log.info("Listed: " + e.getFirstName() + " " + e.getLastName()));
    }

}
