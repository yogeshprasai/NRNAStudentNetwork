package org.nrna.models.request;

public class PasswordResetWithToken {

    public PasswordResetWithToken(String email, String token, String password) {
        this.email = email;
        this.token = token;
        this.password = password;
    }

    private String email;
    private String token;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
