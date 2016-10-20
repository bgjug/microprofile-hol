package bg.jug.magman.authors.rest;

import bg.jug.magman.authors.domain.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 19.10.16.
 */
@ApplicationScoped
@Path("/")
public class ResourceAuthors {

    /**
     * Gets authors.
     *
     * @return the authors
     */
    @GET
    @Path("/findAll")
    public List<Author> getAuthors() {
        return new ArrayList<>();
    }

    /**
     * Find author by id author.
     *
     * @param authorId the author id
     * @return the author
     */
    @GET
    @Path("/findById/{id}")
    public Author findAuthorById(@PathParam("id") Long authorId) {
        return null;
    }

    /**
     * Find author by names list.
     *
     * @param names the names
     * @return the list
     */
    @GET
    @Path("/findByNames/{names}")
    public List<Author> findAuthorByNames(@PathParam("names") final String names) {
        return new ArrayList<>();
    }

    /**
     * Find regular authors list.
     *
     * @return the list
     */
    @GET
    @Path("/findAllRegular")
    public List<Author> findRegularAuthors() {
        return new ArrayList<>();
    }

    /**
     * Add author.
     *
     * @param author the author
     */
    @POST
    @Path("/add")
    public void addAuthor(final Author author) {

    }

    /**
     * Update author.
     *
     * @param author the author
     */
    @PUT
    @Path("/update")
    public void updateAuthor(final Author author) {

    }

    /**
     * Delete author.
     *
     * @param authorId the author id
     */
    @DELETE
    @Path("/delete/{id}")
    public void deleteAuthor(@PathParam("id") Long authorId) {

    }
}
