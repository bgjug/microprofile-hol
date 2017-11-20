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
package bg.jug.magman.advertisers.domain;

import java.util.Objects;

public class Advertiser {
    private Long id;
    private String name;
    private String website;
    private String contactEmail;
    private SponsorPackage sponsorPackage;

    public Advertiser() {
    }

    public Advertiser(String name, String website, String contactEmail, SponsorPackage sponsorPackage) {
        this(null, name, website, contactEmail, sponsorPackage);
    }

    public Advertiser(Long id, String name, String website, String contactEmail, SponsorPackage sponsorPackage) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.contactEmail = contactEmail;
        this.sponsorPackage = sponsorPackage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public SponsorPackage getSponsorPackage() {
        return sponsorPackage;
    }

    public void setSponsorPackage(SponsorPackage sponsorPackage) {
        this.sponsorPackage = sponsorPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertiser that = (Advertiser) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(website, that.website) &&
                Objects.equals(contactEmail, that.contactEmail) &&
                Objects.equals(sponsorPackage, that.sponsorPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, contactEmail, sponsorPackage);
    }

    @Override
    public String toString() {
        return "Advertiser{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", sponsorPackage=" + sponsorPackage +
                '}';
    }
}
