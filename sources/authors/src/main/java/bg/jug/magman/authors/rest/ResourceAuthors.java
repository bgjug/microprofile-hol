package bg.jug.magman.authors.rest;

import bg.jug.magman.authors.domain.Author;
import bg.jug.magman.authors.persistence.AuthorDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
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
    @Path("/findAll")
    public List<Author> getAuthors() {
        return authorDAO.getAuthors();
    }

    @GET
    @Path("/findById/{id}")
    public Author findAuthorById(@PathParam("id") Long authorId) {
        return authorDAO.findAuthorById(authorId);
    }


    @GET
    @Path("/findByNames/{names}")
    public List<Author> findAuthorByNames(@PathParam("names") final String names) {
        return authorDAO.findAuthorByNames(names);
    }

    @GET
    @Path("/findAllRegular")
    public List<Author> findRegularAuthors() {
        return authorDAO.findRegularAuthors();
    }

    @POST
    @Path("/add")
    public void addAuthor(final Author author) {
        authorDAO.addAuthor(author);
    }

    @PUT
    @Path("/update")
    public void updateAuthor(final Author author) {
        authorDAO.updateAuthor();
    }

    @DELETE
    @Path("/delete/{id}")
    public void deleteAuthor(@PathParam("id") Long authorId) {
        authorDAO.deleteAuthor(authorId);
    }
}
