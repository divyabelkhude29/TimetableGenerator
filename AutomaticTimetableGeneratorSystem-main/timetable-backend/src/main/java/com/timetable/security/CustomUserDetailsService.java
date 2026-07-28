package com.timetable.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.UserDAO;
import com.timetable.entity.User;

@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Load User by Username
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userDAO.findByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException(
                    "User not found with username: " + username);
        }

        return new CustomUserDetails(user);
    }

    /**
     * Load User by User ID
     * Useful for JWT authentication after extracting userId.
     */
    public UserDetails loadUserById(Long userId) {

        User user = userDAO.findById(userId);

        if (user == null) {

            throw new UsernameNotFoundException(
                    "User not found with ID: " + userId);
        }

        return new CustomUserDetails(user);
    }

    /**
     * Get User Entity by Username
     * Useful in Service Layer if required.
     */
    public User getUserByUsername(String username) {

        User user = userDAO.findByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException(
                    "User not found with username: " + username);
        }

        return user;
    }

    /**
     * Get User Entity by User ID
     */
    public User getUserById(Long userId) {

        User user = userDAO.findById(userId);

        if (user == null) {

            throw new UsernameNotFoundException(
                    "User not found with ID: " + userId);
        }

        return user;
    }

}