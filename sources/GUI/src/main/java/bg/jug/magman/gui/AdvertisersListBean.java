package bg.jug.magman.gui;

import bg.jug.magman.domain.Advertiser;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@ManagedBean
@ViewScoped
public class AdvertisersListBean {

    private List<Advertiser> advertisers;

    @PostConstruct
    private void init(){
        advertisers = new ArrayList<>(0);
    }

    public List<Advertiser> getAdvertisers() {
        return advertisers;
    }

    public void setAdvertisers(List<Advertiser> advertisers) {
        this.advertisers = advertisers;
    }
}
