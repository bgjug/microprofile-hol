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
package bg.jug.magman.advertisers.resources;

import bg.jug.magman.advertisers.dao.AdvertiserDAO;
import bg.jug.magman.advertisers.domain.Advertiser;
import bg.jug.magman.advertisers.rest.Application;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/")
@Produces("application/json")
@RequestScoped
public class AdvertiserResource {

    @Inject
    private AdvertiserDAO advertiserDAO;

    @GET
    public Response getAdvertisers() {
        final List<Advertiser> advertisers = advertiserDAO.getAdvertisers();
        GenericEntity<List<Advertiser>> advertisersGE = buildGenericEntity(advertisers);
        return Response.ok(advertisersGE).build();
    }

    @GET
    @Path("/{id}")
    public Response findAdvertiserById(@PathParam("id") final Long id) {
        return advertiserDAO.findAdvertiserById(id)
                .map(advertiser -> Response.ok(advertiser).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/name/{name}")
    public Response findAdvertiserByName(@PathParam("name") final String name) {
        return advertiserDAO.findAdvertiserByName(name)
                .map(advertiser -> Response.ok(advertiser).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/package/{identifier}")
    public Response findAdvertisersByPackage(@PathParam("identifier") final String identifier) {
        final List<Advertiser> advertisersByPackage = advertiserDAO.findAdvertisersByPackage(identifier);
        final GenericEntity<List<Advertiser>> entity = buildGenericEntity(advertisersByPackage);
        return Response.ok(entity).build();
    }

    @POST
    @Consumes("application/json")
    public Response addAdvertiser(final Advertiser advertiser) {
        final Advertiser created = advertiserDAO.addAdvertiser(advertiser);
        return Response.created(URI.create(Application.APPLICATION_PATH + "/" + created.getId()))
                .entity(created)
                .build();
    }

    @PUT
    @Consumes("application/json")
    public Response updateAdvertiser(final Advertiser advertiser) {
        advertiserDAO.updateAdvertiser(advertiser);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response remove(@PathParam("id") final Long advertiserId) {
        advertiserDAO.deleteAdvertiser(advertiserId);
        return Response.noContent().build();
    }

    private GenericEntity<List<Advertiser>> buildGenericEntity(final List<Advertiser> advertisers) {
        return new GenericEntity<List<Advertiser>>(advertisers) {};
    }
}
