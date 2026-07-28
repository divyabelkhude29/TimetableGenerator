package com.timetable.dao;

import java.util.List;

import com.timetable.entity.User;

public interface UserDAO {

    User save(User user);

    User update(User user);

    void delete(Long userId);

    User findById(Long userId);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByMobile(String mobile);

    List<User> findAll();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

}