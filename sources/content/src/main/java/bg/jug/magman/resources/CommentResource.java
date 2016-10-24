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
package bg.jug.magman.resources;

import bg.jug.magman.domain.Article;
import bg.jug.magman.domain.Comment;
import bg.jug.magman.persistence.ArticleDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/comment")
@Produces("application/json")
@RequestScoped
public class CommentResource {

    @Inject
    private ArticleDAO articleDAO;

    @PUT
    @Consumes("application/json")
    public Response updateComment(String commentJson) {
        articleDAO.updateComment(Comment.fromJson(commentJson));
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(Long commentId) {
        articleDAO.deleteComment(commentId);
        return Response.noContent().build();
    }

}
