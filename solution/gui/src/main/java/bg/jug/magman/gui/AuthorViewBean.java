package bg.jug.magman.gui;

import bg.jug.magman.clients.AuthorsClient;
import bg.jug.magman.domain.Author;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
public class AuthorViewBean {

    @Inject
    private AuthorsClient authorsClient;

    private Author author;

    @PostConstruct
    private void init() {
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        author = authorsClient.getAuthorById(Long.valueOf(value));
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
