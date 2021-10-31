package com.example.user.service;

import com.example.exception.APIException;
import com.example.exception.UserNotFoundException;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import com.example.user.request.UserRequest;
import com.example.user.request.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(final UserRequest request) {
        try {
            final User user = new User();
            user.setUserName(request.getUserName());

            this.repository.save(user);
        } catch (final Exception e) {
            throw new APIException(e.getMessage());
        }
    }

    public UserResponse findByUserName(final String userName) {
        final User user = Optional.ofNullable(this.repository.findByUserName(userName))
                .orElseThrow(UserNotFoundException::new);
        return new UserResponse(user.getUserName());
    }

    public List<UserResponse> findAll() {
        try {
            final List<UserResponse> users = new ArrayList<>();
            this.repository.findAll().forEach(user -> {
                final UserResponse response = new UserResponse();
                response.setUserName(user.getUserName());
                users.add(response);
            });
            return users;
        } catch (final Exception e) {
            throw new APIException(e.getMessage());
        }
    }
}
