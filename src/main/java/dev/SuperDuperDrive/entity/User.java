package dev.SuperDuperDrive.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Invalid email")
    @Size(max = 254, message = "Maximum 254 characters")
    private String email;

    private String salt;

    @NotBlank(message = "This field is required")
    private String password;

    @Size(max = 20, message = "Maximum 20 characters")
    @NotBlank(message = "This field is required")
    private String firstname;

    @Size(max = 20, message = "Maximum 20 characters")
    @NotBlank(message = "This field is required")
    private String lastname;

    public User(String email, String password, String firstname, String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
