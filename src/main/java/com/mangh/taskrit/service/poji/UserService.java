package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.User;

import java.util.Optional;

public interface UserService {

    User create (final User user);

    Optional<User> findByEmail(final String email);
}
