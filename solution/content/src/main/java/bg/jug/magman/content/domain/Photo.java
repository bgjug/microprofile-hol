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
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Photo implements Jsonable {

    private Long id;
    private File photoLocation;
    private String author;
    private List<String> tags;

    public Photo() {
    }

    public Photo(File photoLocation, String author) {
        this(photoLocation, author, new ArrayList<>());
    }

    public Photo(File photoLocation, String author, List<String> tags) {
        this(null, photoLocation, author, tags);
    }

    public Photo(Long id, File photoLocation, String author, List<String> tags) {
        this.id = id;
        this.photoLocation = photoLocation;
        this.author = author;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(File photoLocation) {
        this.photoLocation = photoLocation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(photoLocation, photo.photoLocation) &&
                Objects.equals(author, photo.author) &&
                Objects.equals(tags, photo.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoLocation, author, tags);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "author='" + author + '\'' +
                ", tags=" + tags +
                '}';
    }

    public static Photo fromJson(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = reader.readObject();

        Photo photo = new Photo();
        photo.id = jsonObject.getJsonNumber("id").longValue();
        photo.author = jsonObject.getString("author");
        photo.tags = jsonObject.getJsonArray("tags").stream()
                .map(jsonValue -> ((JsonString) jsonValue).getString())
                .collect(Collectors.toList());

        return photo;
    }

    public JsonObject toJson() {
        JsonObjectBuilder photoJson = Json.createObjectBuilder();
        photoJson.add("id", id);
        photoJson.add("author", author);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        tags.forEach(arrayBuilder::add);
        photoJson.add("tags", arrayBuilder.build());
        return photoJson.build();
    }
}
