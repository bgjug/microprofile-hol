package bg.jug.magman.subscribers.rest;

import bg.jug.magman.subscribers.domain.Subscriber;
import bg.jug.magman.subscribers.persistence.SubscribersDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<Subscriber> getSubscribers(){
        return subscribersDAO.getSubscribers();
    }

    @GET
    @Path("/findById/{id}")
    public Subscriber findSubscriberById(@PathParam("id") Long subscriberId){
        return subscribersDAO.findSubscriberById(subscriberId);
    }

    @GET
    @Path("/findExpiring/{days}")
    public List<Subscriber> findExpiringSubscriptions(@PathParam("days") Integer afterDays){
        return subscribersDAO.findExpiringSubscriptions(afterDays);
    }

    @POST
    @Path("/add")
    public void addSubscriber(final Subscriber subscriber){
        subscribersDAO.addSubscriber(subscriber);
    }

    @PUT
    @Path("/update")
    public void updateSubscriber(final Subscriber subscriber){
        subscribersDAO.updateSubscriber(subscriber);
    }

    @PUT
    @Path("/prolong")
    public void prolongSubscription(final Subscriber subscriber, final LocalDate untilDay){
        subscribersDAO.prolongSubscription(subscriber,untilDay);
    }

    @DELETE
    @Path("/delete/{id}")
    public void deleteSubscriber(@PathParam("id") Long subscriberId){
        subscribersDAO.deleteSubscriber(subscriberId);
    }
}
