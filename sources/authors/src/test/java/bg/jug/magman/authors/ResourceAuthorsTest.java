package bg.jug.magman.authors;

import bg.jug.magman.authors.domain.Author;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Dmitry Alexandrov on 24.10.16.
 */

@RunWith(Arquillian.class)
public class ResourceAuthorsTest {

    private final Logger log = Logger.getLogger(ResourceAuthorsTest.class.getName());

    @Deployment(testable = false)
    public static WebArchive deploy() {

        return ShrinkWrap.create(WebArchive.class
                , ResourceAuthorsTest.class.getName() + ".war")
                .addPackage("bg.jug.magman.authors");
    }

    @ArquillianResource
    private URL url;

    @Test
    @RunAsClient
    public void testGet() {
        final Set<Author> authors = this.getWebTarget("authors")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(new GenericType<Set<Author>>() {
                });

        Assert.assertFalse(authors.isEmpty());

        authors.forEach(e->this.log.info("Listed: " + e.getFirstName() + " " + e.getLastName()));
    }


    private WebTarget getWebTarget(final String endpoint) {
        final Client client = ClientBuilder.newBuilder().build();
        return client.target(this.url.toExternalForm() + endpoint);
    }
}
