package bg.jug.magman.gui;

import bg.jug.magman.clients.SubscribersClient;
import bg.jug.magman.domain.Subscriber;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
@ViewScoped
public class SubscribersListBean {

    @Inject
    private SubscribersClient subscribersClient;

    private List<Subscriber> subscribers;

    @PostConstruct
    private void init() {
        subscribers = subscribersClient.getAllSubscribers();
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public void removeSubscriber(long id) {
        subscribersClient.removeSubscriber(id);
    }
}
