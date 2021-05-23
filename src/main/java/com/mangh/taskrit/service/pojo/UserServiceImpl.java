package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.User;
import com.mangh.taskrit.repository.UserRepository;
import com.mangh.taskrit.service.poji.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(final User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return this.userRepository.findByEmail(email);
    }

    public User findByUsername(final String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User updatePassword(final UserDetails userDetails, final String s) {
        return null; //TODO: implement method
    }
}
