package com.example.moldmanagement.dto;

public class JwtResponse {
    private String token;
    private String refreshToken;
    private UserDTO user;

    public JwtResponse(String token, String refreshToken, UserDTO user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserDTO getUser() {
        return user;
    }
}
