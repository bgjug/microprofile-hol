/*
 * Copyright 2016 Microprofile.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bg.jug.magman.persistence;

import bg.jug.magman.domain.Article;
import bg.jug.magman.domain.Comment;
import bg.jug.magman.domain.Photo;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleDAO {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long, Article> articles = new ConcurrentHashMap<>();

    public Article addArticle(Article article) {
        Long articleId = sequence.incrementAndGet();
        article.setId(articleId);

        article.getPhotos().stream()
                            .filter(photo -> photo.getId() == null)
                            .forEach(photo -> photo.setId(sequence.incrementAndGet()));

        article.getComments().stream()
                .filter(comment -> comment.getId() == null)
                .forEach(comment -> comment.setId(sequence.incrementAndGet()));

        articles.put(articleId, article);

        return article;
    }

    public List<Article> getAllArticles() {
        return new ArrayList<>(articles.values());
    }

    public Optional<Article> findArticleById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    public List<Article> findArticlesByAuthor(String authorName) {
        return articles.values().stream()
                                .filter(article -> article.getAuthor().contains(authorName))
                                .collect(Collectors.toList());
    }

    public Article updateArticle(Article article) {
        if (article.getId() == null) {
            return addArticle(article);
        }

        articles.put(article.getId(), article);
        return article;
    }

    public void deleteArticle(Long articleId) {
        articles.remove(articleId);
    }

    public Comment addCommentToArticle(Comment comment, Long articleId) {
        comment.setId(sequence.incrementAndGet());
        Article article = articles.get(articleId);
        if (article != null) {
            article.getComments().add(comment);
        }

        return comment;
    }

//    public void updateComment(Comment comment) {
//        articles.values().stream()
//                .flatMap(article -> article.getComments().stream())
//                .filter(articleComment -> articleComment.getId().equals(comment.getId()))
//                .findFirst()
//                .ifPresent();
//    }
}
