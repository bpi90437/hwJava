package com.bpi.userservice.dao;

import com.bpi.userservice.entity.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void update(User user);
    void delete(Long id);
}
