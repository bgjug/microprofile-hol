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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticleDAO {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long, Article> articles = new ConcurrentHashMap<>();
    private Map<Long, Comment> comments = new ConcurrentHashMap<>();

    public Article addArticle(Article article) {
        Long articleId = sequence.incrementAndGet();
        article.setId(articleId);

        List<Photo> photos = article.getPhotos();
        if (photos != null) {
            photos.stream()
                .filter(photo -> photo.getId() == null)
                .forEach(photo -> photo.setId(sequence.incrementAndGet()));

        }

        List<Comment> comments = article.getComments();
        if (comments != null) {
            comments.stream()
                    .filter(comment -> comment.getId() == null)
                    .forEach(comment -> comment.setId(sequence.incrementAndGet()));
            comments.forEach(comment -> this.comments.put(comment.getId(), comment));
        }
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
        comments.put(comment.getId(), comment);

        return comment;
    }

    public Comment updateComment(Comment comment) {
        if (comment.getId() != null) {
            comments.put(comment.getId(), comment);
        }
        return comment;
    }

    public void deleteComment(Long commentId) {
        Comment commentToRemove = comments.get(commentId);
        if (commentToRemove != null) {
            comments.remove(commentId);
            articles.values().stream()
                    .filter(article -> articleContainsComment(article, commentId))
                    .findFirst()
                    .ifPresent(article -> article.getComments().remove(commentToRemove));
        }
    }

    private boolean articleContainsComment(Article article, Long commentId) {
        return article.getComments().stream().anyMatch(comment -> comment.getId().equals(commentId));
    }

    public Photo addPhotoToArticle(Photo photo, Long articleId) {
        photo.setId(sequence.incrementAndGet());
        Article article = articles.get(articleId);
        article.getPhotos().add(photo);
        return photo;
    }

    public Optional<Photo> findPhotoById(Long photoId) {
        return articles.values().stream()
                .flatMap(article -> article.getPhotos().stream())
                .filter(photo -> photo.getId().equals(photoId))
                .findFirst();
    }

    @PostConstruct
    public void insertTestData() {
        Article article1 = new Article("Bulgarian JUG’s 2015", "2015 is over and 2016 is a week old now. However, I can’t forget the past year, which happened to be the most active one for the Bulgarian JUG, where I happen to be one of the co-leads. And what a year it was! We had everything: seminar talks with local and foreign speakers, hands on labs, Adopt OpenJDK and Adopt a JSR hackathons, a code retreat and a big international conference. In this blog post I will briefly go through all the events that kept our community busy in 2015.", "Bilbo Baggins");
        Article article2 = new Article("Primitives in Generics, part 3", "In the Bulgarian JUG we had an event dedicated to trying out the OpenJDK Valhalla project’s achievements in the area of using primitive parameters of generics. Our colleague and blogger Mihail Stoynov already wrote about our workshop. I decided, though, to go in a little bit more details and explain the various aspects of the feature.", "Spider Man");
        Article article3 = new Article("Primitives in Generics, part 2", "Whenever the OpenJDK developers want to experiment with a concept they first create a dedicated OpenJDK project for that. This project usually has its own source repository, which is a fork of the OpenJDK sources. It has its page and mailing list and its main purpose is to experiment with ideas for implementing the new concept before creating the Java Enhancement Proposals (JEPs), the Java Specification Requests (JSRs) and committing source code in the real repositories. Features like lambdas, script engine support, method handles and invokedynamic walked this way before entering the official Java release.", "Spider Man");
        Article article4 = new Article("Primitives in Generics, part 1", "Java generics is one of its most widely commented topics. While the discussion whether they should be reified, i.e. the generic parameter information is not erased by javac, is arguably the hottest topic for years now, the lack of support for primitives as parameter types is something that at least causes some confusion. It leads to applying unnecessary boxing when for example you want to put an int into a List (read on to find out about the performance penalty). It also leads to adding “companion” classes in most of the generic APIs, like IntStream and LongStream for example.", "Spider Man");
        Article article5 = new Article("JavaOne, day 4", "The last day at JavaOne started as usual with the community keynote. I didn’t go to it, because I wanted to have a rest after the Aerosmith and Macklemore & Ryan Lewis concert last night and also wanted to catch up with my blogs. However, the people that I follow on twitter were kind enough to update me with the most interesting bits of the session. Additionally, there’s already a blog from Ben Evans about it.", "Captain Power");
        Article article6 = new Article("JavaOne days two and three", "In March this year I had great time at the JavaLand conference. Along with other great people, I met there the freelancer and blog author Roberto Cortez. He told me that he is going to send a few session proposals to JavaOne and asked me whether I wanted to join him for the Java EE Batch talk. I hadn’t heard much about that topic at that time, but I agreed. Then the proposal got accepted and here I am at JavaOne now. What do you know", "Captain Power");

        addArticle(article1);
        addArticle(article2);
        addArticle(article3);
        addArticle(article4);
        addArticle(article5);
        addArticle(article6);
    }
}
