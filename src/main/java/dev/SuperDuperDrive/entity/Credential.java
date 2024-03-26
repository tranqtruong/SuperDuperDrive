package dev.SuperDuperDrive.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Credential {
    private Integer credentialid;
    @NotBlank(message = "url is required")
    @Size(max = 99)
    private String url;

    @NotBlank(message = "username is required")
    @Size(max = 29)
    private String username;

    private String keyy;
    private String password;
    private String email;
}
