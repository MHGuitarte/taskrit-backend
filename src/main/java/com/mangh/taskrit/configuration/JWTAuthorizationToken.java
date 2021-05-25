package com.mangh.taskrit.configuration;

import com.mangh.taskrit.model.UserRole;
import com.mangh.taskrit.util.pojo.Logger;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class JWTAuthorizationToken {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "6U#b*5";

    private final Logger log = new Logger(this.getClass().getName());

    public boolean checkToken(final String userToken) {
        try {
            if (this.checkJWTToken(userToken)) {
                final Claims claims = this.validateToken(userToken);
                if (claims.get("authority") != null) {
                    if (this.setUpAuthentication(claims)) {
                        return true;
                    }
                }
            }

            return false;
        } catch (final ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            this.log.error("Error checking recieved webtoken : {}", e.getMessage());
            return false;
        }
    }

    private Claims validateToken(final String userToken) {
        final String jwtToken = userToken.replace(this.PREFIX, "");
        return Jwts.parser().setSigningKey(this.SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private boolean setUpAuthentication(final Claims claims) {
        final String role = (String) claims.get("authority");

        return Arrays.asList(UserRole.values()).contains(UserRole.valueOf(role));
    }

    private boolean checkJWTToken(final String authenticationHeader) {
        return authenticationHeader != null && authenticationHeader.startsWith(this.PREFIX);
    }
}
