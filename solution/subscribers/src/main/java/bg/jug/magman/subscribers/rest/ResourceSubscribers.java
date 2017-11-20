package bg.jug.magman.subscribers.rest;

import bg.jug.magman.subscribers.domain.Subscriber;
import bg.jug.magman.subscribers.persistence.SubscribersDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 19.10.16.
 */
@RequestScoped
@Path("/")
@Produces("application/json")
public class ResourceSubscribers {

    @Inject
    private SubscribersDAO subscribersDAO;

    @GET
    @Path("/")
    @Produces("application/json")
    public Response getSubscribers() {
        return Response.ok(buildSubscriberJsonArray(subscribersDAO.getSubscribers()).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findSubscriberById(@PathParam("id") Long subscriberId) {
        return Response.ok(buildSubscriberJson(subscribersDAO.findSubscriberById(subscriberId)).build()).build();
    }

    @GET
    @Path("/findExpiring/{days}")
    @Produces("application/json")
    public Response findExpiringSubscriptions(@PathParam("days") Integer afterDays) {
        return Response.ok(buildSubscriberJsonArray(subscribersDAO.findExpiringSubscriptions(afterDays)).build()).build();
    }

    @POST
    @Path("/")
    public void addSubscriber(String subscriberString) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        subscribersDAO.addSubscriber(subscriber);
    }

    @PUT
    @Path("/")
    public void updateSubscriber(String subscriberString) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        subscribersDAO.updateSubscriber(subscriber);
    }

    @PUT
    @Path("/prolong")
    public void prolongSubscription(String subscriberString, String untilDay) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        LocalDate date = LocalDate.parse(untilDay, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        subscribersDAO.prolongSubscription(subscriber, date);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSubscriber(@PathParam("id") Long subscriberId) {
        subscribersDAO.deleteSubscriber(subscriberId);
    }


    // Helpers
    private JsonObjectBuilder buildSubscriberJson(Subscriber subscriber) {
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("address", subscriber.getAddress())
                .add("email", subscriber.getEmail())
                .add("firstName", subscriber.getFirstName())
                .add("lastName", subscriber.getLastName())
                .add("subscribedUntil", subscriber.getSubscribedUntil().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if(subscriber.getId()!=null)
                result.add("id", subscriber.getId());
        return result;
    }

    private JsonArrayBuilder buildSubscriberJsonArray(List<Subscriber> subscribers) {
        JsonArrayBuilder result = Json.createArrayBuilder();
        subscribers.forEach(e -> {
            result.add(buildSubscriberJson(e));
        });
        return result;
    }

    private Subscriber readSubscriberFromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject subscriberObject = reader.readObject();
        reader.close();
        return new Subscriber(Long.valueOf(subscriberObject.getString("id")), subscriberObject.getString("firstName"), subscriberObject.getString("lastName"), subscriberObject.getString("email"), subscriberObject.getString("address"), LocalDate.parse(subscriberObject.getString("subscribedUntil"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
