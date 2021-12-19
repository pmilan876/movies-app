package com.movies.db.repository;

import com.movies.db.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserNameIgnoreCase(String userName);
}
