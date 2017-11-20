package bg.jug.magman.gui;

import bg.jug.magman.clients.ContentClient;
import bg.jug.magman.domain.Article;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Created by Dmitry Alexandrov on 14.11.16.
 */
@Model
public class ArticleAddBean {

    @Inject
    private ContentClient contentClient;

    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void addArticle(){
        contentClient.addArticle(article);
    }
}
