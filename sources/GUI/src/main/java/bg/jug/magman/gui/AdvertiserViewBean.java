package bg.jug.magman.gui;

import bg.jug.magman.clients.AdvertisersClient;
import bg.jug.magman.domain.Advertiser;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by Dmitry Alexandrov on 14.11.16.
 */
public class AdvertiserViewBean {

    @Inject
    private AdvertisersClient advertisersClient;

    private Advertiser advertiser;

    @PostConstruct
    private void init() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        advertiser = advertisersClient.getAdvertiserById(Long.valueOf(value));
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }
}
