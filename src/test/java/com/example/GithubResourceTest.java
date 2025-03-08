package com.example;

import com.example.model.RepositoryResponse;
import com.example.service.GithubService;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class GithubResourceTest {

    @Inject
    GithubService githubService;

    @Test
    public void testGetRepositories() {
        Uni<List<RepositoryResponse>> repositoriesUni = githubService.getNonForkRepositories("torwalds");

        List<RepositoryResponse> repositories = repositoriesUni.await().indefinitely();

        assertThat(repositories, is(not(empty())));
        assertThat(repositories.get(0).getName(), is(not(emptyOrNullString())));
        assertThat(repositories.get(0).getOwner().getLogin(), is(not(emptyOrNullString())));
        assertThat(repositories.get(0).getBranches(), is(not(empty())));
        assertThat(repositories.get(0).getBranches().get(0).name(), is(not(emptyOrNullString())));
        assertThat(repositories.get(0).getBranches().get(0).commit().sha(), is(not(emptyOrNullString())));
    }

    @Test
    public void testUserNotFound() {
        Uni<List<RepositoryResponse>> repositoriesUni = githubService.getNonForkRepositories("nonexistentuser");

        try {
            repositoriesUni.await().indefinitely();
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("User not found"));
        }
    }
}