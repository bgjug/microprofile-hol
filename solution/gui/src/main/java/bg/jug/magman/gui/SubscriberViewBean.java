package bg.jug.magman.gui;

import bg.jug.magman.clients.SubscribersClient;
import bg.jug.magman.domain.Subscriber;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
public class SubscriberViewBean {

    @Inject
    private SubscribersClient subscribersClient;

    private Subscriber subscriber;

    @PostConstruct
    private void init() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        subscriber = subscribersClient.getSubscriberById(Long.valueOf(value));
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

}
