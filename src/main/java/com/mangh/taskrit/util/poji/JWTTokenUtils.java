package com.mangh.taskrit.util.poji;

import com.mangh.taskrit.model.User;

public interface JWTTokenUtils {

    String getJWTToken(final User user, final Boolean saveLogin);
}
