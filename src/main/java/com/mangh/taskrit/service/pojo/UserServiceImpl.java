package com.mangh.taskrit.service.pojo;

import com.mangh.taskrit.model.User;
import com.mangh.taskrit.repository.UserRepository;
import com.mangh.taskrit.service.poji.UserService;
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
    public Optional<User> findByEmail(final String alias) {
        return this.userRepository.findByEmail(alias);
    }
}
