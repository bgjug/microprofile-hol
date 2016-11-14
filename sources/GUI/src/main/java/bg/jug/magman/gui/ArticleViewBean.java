package bg.jug.magman.gui;

import bg.jug.magman.clients.ContentClient;
import bg.jug.magman.domain.Article;
import bg.jug.magman.domain.Comment;

import javax.annotation.PostConstruct;
import javax.faces.bean.NoneScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Alexandrov on 14.11.16.
 */
@Named
@ViewScoped
public class ArticleViewBean {

    @Inject
    private ContentClient contentClient;

    private Article article;

    private Comment newComment;

    private List<Comment> allComments;

    @PostConstruct
    private void init(){
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        article = contentClient.getArticleById(Long.valueOf(value));
        allComments = new ArrayList<>();// currently no service available
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Comment> getAllComments() {
        return allComments;
    }

    public void setAllComments(List<Comment> allComments) {
        this.allComments = allComments;
    }

    public Comment getNewComment() {
        return newComment;
    }

    public void setNewComment(Comment newComment) {
        this.newComment = newComment;
    }

    public void addComment(){
        contentClient.addCommentToArticle(newComment,article);
    }


}
