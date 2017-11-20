/*
 * Copyright 2016 Microprofile.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bg.jug.magman.advertisers.dao;

import bg.jug.magman.advertisers.domain.Advertiser;
import bg.jug.magman.advertisers.domain.SponsorPackage;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class AdvertiserDAO {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long, Advertiser> advertisers = new ConcurrentHashMap<>();

    public List<Advertiser> getAdvertisers() {
        return new ArrayList<>(advertisers.values());
    }

    public Optional<Advertiser> findAdvertiserById(Long id) {
        return Optional.ofNullable(advertisers.get(id));
    }

    public Optional<Advertiser> findAdvertiserByName(String name) {
        return advertisers.values()
                .stream()
                .filter(advertiser -> advertiser.getName().equals(name))
                .findFirst();
    }

    public List<Advertiser> findAdvertisersByPackage(String packageIdentifier) {
        Map<SponsorPackage, List<Advertiser>> groupedPackages = advertisers
                .values().stream()
                .collect(Collectors.groupingBy(Advertiser::getSponsorPackage));
        return groupedPackages.get(SponsorPackage.valueOf(packageIdentifier));
    }

    public Advertiser addAdvertiser(Advertiser advertiser) {
        Long advertiserId = sequence.incrementAndGet();
        advertiser.setId(advertiserId);

        advertisers.put(advertiserId, advertiser);

        return advertiser;
    }

    public Advertiser updateAdvertiser(Advertiser advertiser) {
        if (advertiser.getId() == null) {
            return addAdvertiser(advertiser);
        }

        advertisers.put(advertiser.getId(), advertiser);
        return advertiser;
    }

    public void deleteAdvertiser(Long id) {
        advertisers.remove(id);
    }

    @PostConstruct
    public void addTestData() {
        Advertiser acme = new Advertiser("ACME Co", "www.acme.com", "management@acme.com", SponsorPackage.GOLD);
        Advertiser jugg = new Advertiser("BG JUGG", "www.jugg.bg", "callme@jugg.bg", SponsorPackage.GOLD);
        Advertiser jpream = new Advertiser("jPream", "www.jpream.io", "admin@jpream.io", SponsorPackage.SILVER);
        Advertiser bestCompany = new Advertiser("Best Company", "www.bestcompany.java", "manager@bestcompany.java", SponsorPackage.BRONZE);

        addAdvertiser(acme);
        addAdvertiser(jugg);
        addAdvertiser(jpream);
        addAdvertiser(bestCompany);
    }
}
