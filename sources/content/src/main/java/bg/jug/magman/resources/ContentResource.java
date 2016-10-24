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
package bg.jug.magman.resources;

import bg.jug.magman.domain.Article;
import bg.jug.magman.domain.Comment;
import bg.jug.magman.domain.Jsonable;
import bg.jug.magman.domain.Photo;
import bg.jug.magman.persistence.ArticleDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ContentResource {

    @Inject
    private ArticleDAO articleDAO;

    @GET
    public Response getArticles() {
        List<Article> articles = articleDAO.getAllArticles();
        JsonArray articlesArray = buildJsonArray(articles);
        return Response.ok(articlesArray).build();
    }

    @GET
    @Path("/{id}")
    public Response findArticleById(@PathParam("id") final Long articleId) {
        return articleDAO.findArticleById(articleId)
                .map(article -> Response.ok(article ).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/author/{authorName}")
    public Response findArticlesByAuthor(@PathParam("authorName") String authorName) {
        List<Article> articles = articleDAO.findArticlesByAuthor(authorName);
        JsonArray articlesArray = buildJsonArray(articles);
        return Response.ok(articlesArray).build();

    }

    @POST
    @Consumes("application/json")
    public Response addArticle(String articleJson) {
        final Article article = Article.fromJson(articleJson);
        final Article created = articleDAO.addArticle(article);
        return Response.created(URI.create("/" + created.getId()))
                .entity(created.toJson())
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateArticle(String articleJson) {
        articleDAO.updateArticle(Article.fromJson(articleJson));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") Long articleId) {
        articleDAO.deleteArticle(articleId);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCommentToArticle(@PathParam("id") Long articleId, String commentJson) {
        Comment comment = Comment.fromJson(commentJson);
        Comment created = articleDAO.addCommentToArticle(comment, articleId);
        return Response.created(URI.create("/" + created.getId()))
                .entity(created.toJson())
                .build();
    }

    @POST
    @Path("/{id}/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addPhotoToArticle(@PathParam("id") Long articleId,
                                      @Context HttpServletRequest servletRequest) {
        try {
            final Part photoPart = servletRequest.getPart("photo");
            final File localFile = new File(System.getProperty("java.io.tmpdir"), photoPart.getName());
            try (InputStream in = photoPart.getInputStream()) {
                Files.copy(in, localFile.toPath());
            }
            Photo photo = Photo.fromJson(servletRequest.getParameter("photoData"));
            photo.setPhotoLocation(localFile);
            Photo created = articleDAO.addPhotoToArticle(photo, articleId);
            return Response.created(URI.create("/photo/" + created.getId()))
                    .entity(photo.toJson())
                    .build();
        } catch (IOException | ServletException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/photo")
    public Response getPhotosForArticle(@PathParam("id") Long articleId) {
        return articleDAO.findArticleById(articleId)
                .map(Article::getPhotos)
                .map(photosList -> Response.ok(buildJsonArray(photosList)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @GET
    @Produces({"image/png", "image/jpg"})
    @Path("/photo/{id}")
    public Response getPhoto(@PathParam("id") Long photoId) {
        return articleDAO.findPhotoById(photoId)
                .map(Photo::getPhotoLocation)
                .map(imageFile -> Response.ok(imageFile).header("Content-Disposition", "attachment; filename=\"" + imageFile.getName() + "\"").build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    private <T extends Jsonable> JsonArray buildJsonArray(List<T> jsonables) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        jsonables.forEach(jsonable -> arrayBuilder.add(jsonable.toJson()));
        return arrayBuilder.build();
    }
}
