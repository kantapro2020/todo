package com.todo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.beans.User;
import com.todo.repositories.UserRepository;

@Service
public class GetUserDataService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUser() {
        List<User> allUser = userRepository.getAllUser();
        return allUser;
    }

    public User getUser(int id) {
        User user = userRepository.getUser(id);
        return user;
    }
}
