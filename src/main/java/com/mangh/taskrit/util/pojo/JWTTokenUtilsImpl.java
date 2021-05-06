package com.mangh.taskrit.util.pojo;

import com.mangh.taskrit.model.User;
import com.mangh.taskrit.util.poji.JWTTokenUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTTokenUtilsImpl implements JWTTokenUtils {

    @Value("${user.token.expiration}")
    private long EXPIRATION_TOKEN; //10 min expiration

    @Value("${admin.token.access.expiration}")
    private long EXPIRATION_ADMIN_TOKEN; //3 min expiration

    private final String secretKey = "6U#b*5";
    private final String authId = "tinmdc";

    @Override
    public String getJWTToken(final User user) {
        return this.buildToken(user, this.EXPIRATION_TOKEN);
    }

    @Override
    public boolean checkToken(final String requestPass, final String userPass) {
        return requestPass.equals(userPass);
    }

    @Override
    public String getAdminToken(final User u) {

        return this.buildToken(u, this.EXPIRATION_ADMIN_TOKEN);
    }

    private String buildToken(final User user, final long expiration) {

        return "Bearer " + Jwts.builder() //
                .setId(this.authId) //
                .setSubject(user.getUsername()) //
                .claim("authorities", user.getAuthorities().stream() //
                        .map(GrantedAuthority::getAuthority)  //
                        .collect(Collectors.toList())) //
                .setIssuedAt(new Date(System.currentTimeMillis())) //
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) //
                .signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes()).compact();
    }
}
