package dev.SuperDuperDrive.service;

import dev.SuperDuperDrive.entity.Token;
import dev.SuperDuperDrive.mapper.TokenMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class TokenService {
    private final String SECRET_KEY = "7wPz*!k2@L9s#FgR";
    private final Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3);

    private TokenMapper tokenMapper;

    public TokenService(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    public String generateToken(String email) throws Exception {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        tokenMapper.insertToken(new Token(token));
        return token;
    }

    public int markAsUsed(String tokenCode) {
        Token token = new Token(tokenCode);
        token.setUsed(true);
        return tokenMapper.updateToken(token);
    }

    public String validateToken(String token) {
        Token tokenObj = tokenMapper.selectToken(token);
        if (tokenObj != null && tokenObj.getUsed()) {
            return "Token has been used";
        }

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return "Token is valid";
        } catch (ExpiredJwtException e) {
            if (tokenObj != null) {
                tokenMapper.deleteToken(token);
            }
            return "Token has expired";
        } catch (SignatureException e) {
            return "Invalid signature";
        } catch (MalformedJwtException e) {
            return "Invalid token";
        } catch (Exception e) {
            return "Unknown error";
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
