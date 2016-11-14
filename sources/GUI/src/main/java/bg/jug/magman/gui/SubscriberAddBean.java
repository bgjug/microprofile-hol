package bg.jug.magman.gui;

import bg.jug.magman.clients.SubscribersClient;
import bg.jug.magman.domain.Subscriber;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
public class SubscriberAddBean {

    @Inject
    private SubscribersClient subscribersClient;

    private Subscriber subscriber = new Subscriber();

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public void addSubscriber(){
        subscribersClient.addSubscriber(subscriber);
    }
}
