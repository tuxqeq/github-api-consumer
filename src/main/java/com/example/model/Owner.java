package com.example.model;

public record Owner (
        String login
){
    public Owner(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
