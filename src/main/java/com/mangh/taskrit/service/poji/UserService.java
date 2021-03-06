package com.mangh.taskrit.service.poji;

import com.mangh.taskrit.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User create (final User user);

    Optional<User> findByEmail(final String email);

    Optional<User> findByUsername(final String username) throws UsernameNotFoundException;

    Optional<User> findById(final UUID userId);

    User updatePassword(final UserDetails userDetails, final String s);
}
