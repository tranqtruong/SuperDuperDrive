package dev.SuperDuperDrive.config;

import dev.SuperDuperDrive.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.sql.DataSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationService authenticationService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .authenticationProvider(authenticationService)
                .userDetailsService(userDetailsService);
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Autowired
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Bean
    SecurityFilterChain customfilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(mvc.pattern("/signup")).permitAll()
                .requestMatchers(mvc.pattern("/reset-pass/**")).permitAll()
                .requestMatchers(mvc.pattern("/resetpwd")).permitAll()
                .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        // .successHandler(myAuthenticationSuccessHandler())
                        .defaultSuccessUrl("/home", true)
                        .loginProcessingUrl("/login")
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .successHandler(myAuthenticationSuccessHandler))
                .logout(logout -> logout
                        .permitAll())
                .rememberMe(remember -> remember
                        .key("my-key")
                        .rememberMeParameter("remember-me")
                        .tokenRepository(persistentTokenRepository()));

        // http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(options -> options.disable()));

        return http.build();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
