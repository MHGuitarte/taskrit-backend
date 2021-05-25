package com.mangh.taskrit.util.pojo;

import com.mangh.taskrit.model.User;
import com.mangh.taskrit.util.poji.JWTTokenUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenUtilsImpl implements JWTTokenUtils {

    @Value("${user.token.expiration}")
    private long EXPIRATION_TOKEN; //2 day expiration

    @Value("${persist.login.token.expiration}")
    private long SAVE_LOGIN_TOKEN; //1 week expiration

    private final String secretKey = "6U#b*5";
    private final String authId = "tskmdc";

    @Override
    public String getJWTToken(final User user, final Boolean saveLogin) {
        return buildToken(user, saveLogin);
    }

    private String buildToken(final User user, final Boolean saveLogin) {

        return Jwts.builder() //
                .setId(authId) //
                .setSubject(user.getUsername()) //
                .claim("authority", user.getRole()) //
                .setIssuedAt(new Date(System.currentTimeMillis())) //
                .setExpiration(new Date(System.currentTimeMillis() + (Boolean.TRUE.equals(saveLogin) ? EXPIRATION_TOKEN : SAVE_LOGIN_TOKEN))) //
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
    }
}
