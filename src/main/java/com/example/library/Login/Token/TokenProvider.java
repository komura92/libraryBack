package com.example.library.Login.Token;

import com.example.library.Login.MyUserDetails;
import com.example.library.Service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenProvider {

    private final int tokenValidityInMilis;
    private final String secretKey;
    private final MyUserDetails myUserDetails;
    private final UserService userService;

    public TokenProvider(
            @Value("${security.token.expired-time}") int tokenValidityInMilis,
            @Value("${security.token.secret-key}") String secretKey,
            MyUserDetails myUserDetails,
            UserService userService) {

        this.tokenValidityInMilis = tokenValidityInMilis;
        this.secretKey = secretKey;
        this.myUserDetails = myUserDetails;
        this.userService = userService;
    }

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilis);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(now).signWith(SignatureAlgorithm.HS512, this.secretKey)
                .setExpiration(validity).compact();
    }

    public Authentication getAuthentication(String token) {
        String username = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject();

        UserDetails userDetails = userService.getUserByUsername(username);
//        UserDetails userDetails = MyUserDetails.getActualUser();

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
