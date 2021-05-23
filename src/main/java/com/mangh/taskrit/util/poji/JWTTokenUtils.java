package com.mangh.taskrit.util.poji;

import com.mangh.taskrit.model.User;

public interface JWTTokenUtils {

    public String getJWTToken(final User user, final Boolean saveLogin);

    public boolean checkToken(final String requestPass,final String userPass);
}
