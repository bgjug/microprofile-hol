package bg.jug.magman.gui;

import bg.jug.magman.clients.ContentClient;
import bg.jug.magman.domain.Article;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@Model
@ViewScoped
public class ArticlesListBean {

    @Inject
    private ContentClient contentClient;

    private List<Article> articles;

    @PostConstruct
    private void init(){
        articles = contentClient.getAllArticles();
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void removeArticle(long id){
        contentClient.removeArticle(id);
    }
}
