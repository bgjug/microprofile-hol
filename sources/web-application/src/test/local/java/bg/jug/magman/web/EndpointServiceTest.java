
package bg.jug.magman.web;


import bg.jug.magman.web.Application;
import bg.jug.magman.web.Endpoint;
import bg.jug.magman.web.EndpointService;
import bg.jug.magman.web.Endpoints;
import org.apache.cxf.jaxrs.client.WebClient;
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

import javax.ws.rs.core.MediaType;
import java.net.URL;

@RunWith(Arquillian.class)
public class EndpointServiceTest extends Assert {


    @Deployment(testable = false)
    public static WebArchive createDeployment() throws Exception {
        return ShrinkWrap.create(WebArchive.class).addClasses(Application.class, Endpoint.class, Endpoints.class, EndpointService.class)
                .addAsWebInfResource("conference.properties", "conference.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }


    @ArquillianResource
    private URL url;

    @Test
    @RunAsClient
    public void getEndpoints() throws Exception {

        final WebClient webClient = WebClient.create(this.url.toURI());
        webClient.accept(MediaType.APPLICATION_JSON);

        final String reset = webClient.path("/service/endpoints/").get(String.class);
        Assert.assertNotNull("Failed to reset", reset);

        final Endpoints eps = webClient.path("magman")
                .get(Endpoints.class);

        Assert.assertNotNull("Failed to get Endpoints", eps);
        Assert.assertFalse("Endpoints is empty", eps.getEndpoints().isEmpty());
    }

}
