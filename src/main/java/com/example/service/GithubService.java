package com.example.service;

import com.example.client.GithubClient;
import com.example.model.GithubBranch;
import com.example.model.RepositoryResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class GithubService {

    @Inject
    @RestClient
    GithubClient githubClient;

    public Uni<List<RepositoryResponse>> getNonForkRepositories(String username) {
        return githubClient.getRepositories(username)
                .onFailure().retry().withBackOff(Duration.ofSeconds(1), Duration.ofSeconds(10)).atMost(3)
                .onItem().transformToUni(repos -> {
                    var unis = repos.stream()
                            .filter(repo -> !repo.isFork())
                            .map(this::fetchBranches)
                            .toList();
                            if (repos.isEmpty()) {
                                throw new WebApplicationException("User not found", Response.Status.NOT_FOUND);
                            }

                    return Uni.join().all(unis).andFailFast()
                            .onItem().transform(results -> results.stream()
                                    .map(o -> (RepositoryResponse) o)
                                    .toList());
                });
    }

    private Uni<RepositoryResponse> fetchBranches(RepositoryResponse repo) {
        if (repo.owner() == null || repo.name() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Repository has invalid owner or name"));
        }

        return githubClient.getBranches(repo.owner().login(), repo.name())
                .onFailure().retry().withBackOff(Duration.ofSeconds(1), Duration.ofSeconds(10)).atMost(3)
                .onItem().transform(branches -> {
                    List<GithubBranch> branchInfos = branches.stream()
                            .map(branch -> new GithubBranch(branch.name(), branch.commit()))
                            .toList();
                    return new RepositoryResponse(
                            repo.name(),
                            repo.owner(),
                            repo.fork(),
                            branchInfos
                    );
                });
    }
}