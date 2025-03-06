package org.nrna.dto.request;

public class VerifyToken {

    public VerifyToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    private String email;
    private String token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
