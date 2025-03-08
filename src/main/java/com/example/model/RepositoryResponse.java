package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RepositoryResponse(
        String name,
        Owner owner,
        boolean fork,
        List<GithubBranch> branches
        ){


    public RepositoryResponse(String name, Owner owner, boolean fork, List<GithubBranch> branches) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches = branches;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }


    @JsonProperty("fork")
    public boolean isFork() {
        return fork;
    }

    @JsonProperty("branches")
    public List<GithubBranch> getBranches() {
        return branches != null ? branches : List.of();    }
}