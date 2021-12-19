package com.movies.db.service;

import com.google.common.collect.Lists;
import com.movies.db.entity.Movie;
import com.movies.db.entity.User;
import com.movies.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
    public User getByUserName(String userName) {
        return userRepository.findByUserNameIgnoreCase(userName);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public User update(User user) {
        return userRepository.save(user);
    }

}
