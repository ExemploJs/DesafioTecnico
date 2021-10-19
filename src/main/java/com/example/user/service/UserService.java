package com.example.user.service;

import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    public void create(final User user) {
        this.repository.save(user);
    }

    public User findByUserName(final String userName) {
        return this.repository.findByUserName(userName);
    }

    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        this.repository.findAll().forEach(users::add);
        return users;
    }
}
