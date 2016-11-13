package bg.jug.magman.gui;

import bg.jug.magman.domain.Article;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@ManagedBean
@ViewScoped
public class ArticlesListBean {

    private List<Article> articles;

    @PostConstruct
    private void init(){
        articles = new ArrayList<>(0);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
