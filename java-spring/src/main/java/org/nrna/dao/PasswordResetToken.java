package org.nrna.dao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class PasswordResetToken {

    private static final int TOKEN_EXPIRATION = 24 * 60;

    public PasswordResetToken() {
    }

    public PasswordResetToken(User user, String token, LocalDateTime expirationDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expirationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime expiryDate;

    private boolean expired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
