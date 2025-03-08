package com.example.model;

public record GithubBranch(
        String name,
        Commit commit
) {}
