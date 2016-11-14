package bg.jug.magman.authors.rest;

import bg.jug.magman.authors.domain.Author;
import bg.jug.magman.authors.persistence.AuthorDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 19.10.16.
 */
@ApplicationScoped
@Path("/")
public class ResourceAuthors {

    @Inject
    private AuthorDAO authorDAO;

    @GET
    @Path("/")
    @Produces("application/json")
    public Response getAuthors() {
        return Response.ok(buildAuthorJsonArray(authorDAO.getAuthors()).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findAuthorById(@PathParam("id") Long authorId) {
        return Response.ok(buildAuthorJson(authorDAO.findAuthorById(authorId)).build()).build();
    }


    @GET
    @Path("/findByNames/{names}")
    @Produces("application/json")
    public Response findAuthorByNames(@PathParam("names") final String names) {
        return Response.ok(buildAuthorJsonArray(authorDAO.findAuthorByNames(names)).build()).build();
    }

    @GET
    @Path("/findAllRegular")
    @Produces("application/json")
    public Response findRegularAuthors() {
        return Response.ok(buildAuthorJsonArray(authorDAO.findRegularAuthors()).build()).build();
    }

    @POST
    @Path("/")
    public void addAuthor(@QueryParam("Author") String author) {
        authorDAO.addAuthor(readAuthorFromJson(author));
    }

    @PUT
    @Path("/update")
    public void updateAuthor(@QueryParam("Author") String author) {
        authorDAO.updateAuthor(readAuthorFromJson(author));
    }

    @DELETE
    @Path("/{id}")
    public void deleteAuthor(@PathParam("id") Long authorId) {
        authorDAO.deleteAuthor(authorId);
    }

    private JsonObjectBuilder buildAuthorJson(Author author){
        JsonObjectBuilder result = Json.createObjectBuilder();
        result.add("lastName",author.getLastName())
        .add("firstName",author.getFirstName())
        .add("email",author.getEmail())
        .add("salary",author.getSalary())
        .add("regular",author.isRegular());
        if (author.getId()!=null) result.add("id",author.getId());
        return result;
    }

    private JsonArrayBuilder buildAuthorJsonArray(List<Author> authors){
        JsonArrayBuilder result = Json.createArrayBuilder();
        authors.forEach(e->{
            result.add(buildAuthorJson(e));
        });
        return result;
    }

    private Author readAuthorFromJson(String json){
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject authorObject = reader.readObject();
        reader.close();
        return new Author(Long.valueOf(authorObject.getString("id")),authorObject.getString("firstName"),authorObject.getString("lastName"),authorObject.getString("email"),Boolean.valueOf(authorObject.getString("regular")),Integer.valueOf(authorObject.getString("salary")));
    }
}
