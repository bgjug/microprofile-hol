package bg.jug.magman.gui;

import bg.jug.magman.clients.AdvertisersClient;
import bg.jug.magman.domain.Advertiser;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
@ViewScoped
public class AdvertisersListBean {

    @Inject
    private AdvertisersClient advertisersClient;

    private List<Advertiser> advertisers;

    @PostConstruct
    private void init(){
        advertisers = advertisersClient.getAllAdvertisers();
    }

    public List<Advertiser> getAdvertisers() {
        return advertisers;
    }

    public void setAdvertisers(List<Advertiser> advertisers) {
        this.advertisers = advertisers;
    }

    public void removeAdvertiser(long id){
        advertisersClient.removeAdvertiser(id);
    }
}
