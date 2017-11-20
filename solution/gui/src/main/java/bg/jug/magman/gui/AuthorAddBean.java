package bg.jug.magman.gui;

import bg.jug.magman.clients.AuthorsClient;
import bg.jug.magman.domain.Author;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
public class AuthorAddBean {

    @Inject
    private AuthorsClient authorsClient;

    private Author author = new Author();

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void addAuthor(){
        authorsClient.addAuthor(author);
    }
}
