package bg.jug.magman.gui;

import bg.jug.magman.clients.AuthorsClient;
import bg.jug.magman.domain.Author;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
@ViewScoped
public class AuthorsListBean {

    @Inject
    private AuthorsClient authorsClient;

    private List<Author> authors;

    @PostConstruct
    private void init(){
       authors = authorsClient.getAllAuthors();
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void removeAuthor(long id){
        authorsClient.removeAuthor(id);
    }
}
