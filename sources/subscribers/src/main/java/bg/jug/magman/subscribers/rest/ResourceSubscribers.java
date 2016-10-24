package bg.jug.magman.subscribers.rest;

import bg.jug.magman.subscribers.domain.Subscriber;
import bg.jug.magman.subscribers.persistence.SubscribersDAO;

import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
@Path("/")
public class ResourceSubscribers {

    @Inject
    SubscribersDAO subscribersDAO;

    @GET
    @Path("/findAll")
    public List<Subscriber> getSubscribers() {
        return subscribersDAO.getSubscribers();
    }

    @GET
    @Path("/findById/{id}")
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
    @Path("/add")
    public void addSubscriber(@QueryParam("Subscriber") String subscriberString) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        subscribersDAO.addSubscriber(subscriber);
    }

    @PUT
    @Path("/update")
    public void updateSubscriber(@QueryParam("Subscriber") String subscriberString) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        subscribersDAO.updateSubscriber(subscriber);
    }

    @PUT
    @Path("/prolong")
    public void prolongSubscription(@QueryParam("Subscriber") String subscriberString, @QueryParam("untilDay") String untilDay) {
        Subscriber subscriber = readSubscriberFromJson(subscriberString);
        LocalDate date = LocalDate.parse(untilDay, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        subscribersDAO.prolongSubscription(subscriber, date);
    }

    @DELETE
    @Path("/delete/{id}")
    public void deleteSubscriber(@PathParam("id") Long subscriberId) {
        subscribersDAO.deleteSubscriber(subscriberId);
    }


    // Helpers
    private JsonObjectBuilder buildSubscriberJson(Subscriber subscriber) {
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("Address", subscriber.getAddress())
                .add("Email", subscriber.getEmail())
                .add("FirstName", subscriber.getFirstName())
                .add("LastName", subscriber.getLastName())
                .add("SubscribedUntil", subscriber.getSubscribedUntil().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        if(subscriber.getId()!=null)
                result.add("Id", subscriber.getId());
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
        return new Subscriber(Long.valueOf(subscriberObject.getString("Id")), subscriberObject.getString("FirstName"), subscriberObject.getString("LastName"), subscriberObject.getString("Email"), subscriberObject.getString("Address"), LocalDate.parse(subscriberObject.getString("SubscribedUntil"), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
