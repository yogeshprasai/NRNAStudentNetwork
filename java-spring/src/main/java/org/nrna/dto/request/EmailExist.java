package org.nrna.dto.request;

import javax.validation.constraints.NotBlank;

public class EmailExist {

    @NotBlank
    private String email;

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }
}
