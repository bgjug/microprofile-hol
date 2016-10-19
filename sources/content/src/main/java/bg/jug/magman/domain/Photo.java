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
package bg.jug.magman.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Photo {

    private Long id;
    private byte[] photo;
    private String author;
    private List<String> tags;

    public Photo() {
    }

    public Photo(byte[] photo, String author) {
        this(photo, author, new ArrayList<>());
    }

    public Photo(byte[] photo, String author, List<String> tags) {
        this(null, photo, author, tags);
    }

    public Photo(Long id, byte[] photo, String author, List<String> tags) {
        this.id = id;
        this.photo = photo;
        this.author = author;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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
        Photo photo1 = (Photo) o;
        return Arrays.equals(photo, photo1.photo) &&
                Objects.equals(author, photo1.author) &&
                Objects.equals(tags, photo1.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photo, author, tags);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "author='" + author + '\'' +
                ", tags=" + tags +
                '}';
    }
}
