package com.example.resource;

import com.example.service.GithubService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
public class GithubResource {

    @Inject
    GithubService githubService;

    @GET
    @Path("/users/{username}/repos")
    public Uni<Response> getRepositories(@PathParam("username") String username) {
        return githubService.getNonForkRepositories(username)
                .onItem().transform(repos -> Response.ok(repos).build())
                .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(404, "User not found"))
                        .build());
    }
}

