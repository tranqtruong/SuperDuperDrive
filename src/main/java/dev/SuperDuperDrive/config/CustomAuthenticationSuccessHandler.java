package dev.SuperDuperDrive.config;

import dev.SuperDuperDrive.entity.User;
import dev.SuperDuperDrive.service.RandomService;
import dev.SuperDuperDrive.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;
    private RandomService randomService;

    public CustomAuthenticationSuccessHandler(UserService userService, RandomService randomService) {
        this.userService = userService;
        this.randomService = randomService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.isAuthenticated()) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");

            if (!userService.doesUserExist(email)) {
                String firstName = oAuth2User.getAttribute("given_name");
                String lastName = oAuth2User.getAttribute("family_name");
                String password = randomService.generateRandomPassword();

                User user = new User(email, password, firstName, lastName);
                userService.createUser(user);
            }

            response.sendRedirect("/home");
        } else {
            response.sendRedirect("/login");
        }
    }
}
