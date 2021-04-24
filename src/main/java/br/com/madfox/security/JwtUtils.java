package br.com.madfox.security;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    private static final String KEY = "chave_abcde";

    public static String generateToken(Authentication user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Login userWithoutPwd = new Login();
        userWithoutPwd.setUsername(user.getName());
        if (!user.getAuthorities().isEmpty()) {
            userWithoutPwd.setAuthorizer(user.getAuthorities().iterator().next().getAuthority());
        }

        String userJson = mapper.writeValueAsString(userWithoutPwd);
        Date now = new Date();
        Long hour = 1000L * 60L * 60L;

        return Jwts.builder().claim("userDetails", userJson).setIssuer("br.com.madfox").setSubject(user.getName())
                .setExpiration(new Date(now.getTime() + hour)).signWith(SignatureAlgorithm.HS512, KEY).compact();

    }

    public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().get("userDetails",
                String.class);
        Login user = mapper.readValue(credentialsJson, Login.class);
        UserDetails userDetails = User.builder().username(user.getUsername()).password("secret").authorities(user.getAuthorizer())
        .build();
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), userDetails.getAuthorities());
    }
}
