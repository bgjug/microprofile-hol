package bg.jug.magman.gui;

import bg.jug.magman.clients.SubscribersClient;
import bg.jug.magman.domain.Subscriber;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@ManagedBean
@ViewScoped
public class SubscribersListBean {

    @Inject
    private SubscribersClient subscribersClient;

    private List<Subscriber> subscribers;

    @PostConstruct
    private void init(){
        subscribers = subscribersClient.getAllSubscribers();
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }
}
