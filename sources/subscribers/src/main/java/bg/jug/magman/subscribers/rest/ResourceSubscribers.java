package bg.jug.magman.subscribers.rest;

import bg.jug.magman.subscribers.domain.Subscriber;

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

    /**
     * Get subscribers list.
     *
     * @return the list
     */
    @GET
    @Path("/findAll")
    public List<Subscriber> getSubscribers(){
        return new ArrayList<>();
    }

    /**
     * Find subscriber by id subscriber.
     *
     * @param subscriberId the subscriber id
     * @return the subscriber
     */
    @GET
    @Path("/findById/{id}")
    public Subscriber findSubscriberById(@PathParam("id") Long subscriberId){
        return null;
    }

    /**
     * Find expiring subscriptions list.
     *
     * @param afterDays the after days
     * @return the list
     */
    @GET
    @Path("/findExpiring/{days}")
    public List<Subscriber> findExpiringSubscriptions(@PathParam("days") Integer afterDays){
        return new ArrayList<>();
    }

    /**
     * Add subscriber.
     *
     * @param subscriber the subscriber
     */
    @POST
    @Path("/add")
    public void addSubscriber(final Subscriber subscriber){

    }

    /**
     * Update subscriber.
     *
     * @param subscriber the subscriber
     */
    @PUT
    @Path("/update")
    public void updateSubscriber(final Subscriber subscriber){

    }

    /**
     * Prolong subscription.
     *
     * @param subscriber the subscriber
     * @param untilDay   the until day
     */
    @PUT
    @Path("/prolong")
    public void prolongSubscription(final Subscriber subscriber, final LocalDate untilDay){

    }

    /**
     * Delete subscriber.
     *
     * @param subscriberId the subscriber id
     */
    @DELETE
    @Path("/delete/{id}")
    public void deleteSubscriber(@PathParam("id") Long subscriberId){

    }
}
