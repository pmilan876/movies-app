package com.movies.api.controller;


import com.movies.db.entity.User;
import com.movies.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public List<User> getAll() {
        List<User> users = userService.getAll();

        return users;
    }

    @GetMapping(path = "/users/{id}")
    public User getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return user;
    }

    @PostMapping(path = "/users")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping(path = "/users/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
        return "done";
    }

    @DeleteMapping(path = "/users")
    public String deleteAllUsers() {
        userService.deleteAll();
        return "done";
    }

    @PutMapping(path = "/users")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }
}
