package bg.jug.magman.gui;

import bg.jug.magman.clients.AdvertisersClient;
import bg.jug.magman.domain.Advertiser;
import bg.jug.magman.domain.SponsorPackage;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 14.11.16.
 */
@Model
public class AdvertiserAddBean {

    @Inject
    private AdvertisersClient advertisersClient;

    private Advertiser advertiser = new Advertiser();

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public void addAdvertiser(){
        advertisersClient.addAdvertiser(advertiser);
    }

    public List<SponsorPackage> getAllPackages(){
        return Arrays.asList(SponsorPackage.values());
    }
}
