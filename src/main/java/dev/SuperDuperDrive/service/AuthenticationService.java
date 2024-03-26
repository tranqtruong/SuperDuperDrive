package dev.SuperDuperDrive.service;

import dev.SuperDuperDrive.entity.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private UserService userService;
    private HashService hashService;

    public AuthenticationService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        User user = userService.findUserByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("Invalid credentials!");
        }

        String encodedSalt = user.getSalt();
        String hashedPassword = hashService.getHashedValue(pwd, encodedSalt);

        if (!user.getPassword().equals(hashedPassword)) {
            throw new BadCredentialsException("Invalid credentials!");
        }

        return new UsernamePasswordAuthenticationToken(email, pwd, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
