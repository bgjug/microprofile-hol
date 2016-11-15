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
package bg.jug.magman.content.domain;

import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Article implements Jsonable {

    private Long id;
    private String title;
    private String content;
    private String author;
    private List<Photo> photos = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    public Article() {
    }

    public Article(String title, String content, String author) {
        this(null, title, content, author, new ArrayList<>(), new ArrayList<>());
    }

    public Article(String title, String content, String author, List<Photo> photos, List<Comment> comments) {
        this(null, title, content, author, photos, comments);
    }

    public Article(Long id, String title, String content, String author, List<Photo> photos, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.photos = photos;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title) &&
                Objects.equals(content, article.content) &&
                Objects.equals(author, article.author) &&
                Objects.equals(photos, article.photos) &&
                Objects.equals(comments, article.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, author, photos, comments);
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", photos=" + photos +
                ", comments=" + comments +
                '}';
    }

    public static Article fromJson(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();

        Article article = new Article();
        JsonNumber jsonId = jsonObject.getJsonNumber("id");
        if (jsonId != null) {
            article.id = jsonId.longValue();
        }
        article.title = jsonObject.getString("title");
        article.content = jsonObject.getString("content");
        article.author = jsonObject.getString("author");
        JsonArray jsonPhotos = jsonObject.getJsonArray("photos");
        if (jsonPhotos != null) {
            article.photos = jsonPhotos.stream()
                    .map(jsonValue -> Photo.fromJson(jsonValue.toString()))
                    .collect(Collectors.toList());
        }
        JsonArray jsonComments = jsonObject.getJsonArray("comments");
        if (jsonComments != null) {
            article.comments = jsonComments.stream()
                    .map(jsonValue -> Comment.fromJson(jsonValue.toString()))
                    .collect(Collectors.toList());
        }

        return article;
    }

    @Override
    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", id);
        builder.add("title", title);
        builder.add("content", content);
        builder.add("author", author);
        if (photos != null)
            builder.add("photos", getArray(photos));
        if (comments != null)
            builder.add("comments", getArray(comments));
        return builder.build();
    }

    private <T extends Jsonable> JsonArray getArray(List<T> arrayData) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        arrayData.forEach(element -> builder.add(element.toJson().toString()));
        return builder.build();
    }
}
