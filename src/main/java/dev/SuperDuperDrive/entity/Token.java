package dev.SuperDuperDrive.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Token {
    private int id;
    private String token;
    private Boolean used;

    public Token(String token) {
        this.token = token;
    }
}
