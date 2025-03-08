package com.example.client;

import com.example.model.GithubBranch;
import com.example.model.RepositoryResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import java.util.List;


@RegisterRestClient(configKey = "github-api")
@Produces(MediaType.APPLICATION_JSON)
@ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
/*@ClientHeaderParam(name = "Authorization", value = "token {githubToken}")*/
public interface GithubClient {

    @GET
    @Path("/users/{username}/repos")
    Uni<List<RepositoryResponse>> getRepositories(@PathParam("username") String username);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    Uni<List<GithubBranch>> getBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);

    /*default String githubToken() {
        return System.getenv("GITHUB_TOKEN");
    }*/
}
