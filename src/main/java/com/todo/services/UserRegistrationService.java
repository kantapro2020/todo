package com.todo.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.todo.beans.User;
import com.todo.repositories.UserRepository;

@Service
public class UserRegistrationService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public void registerUser(User user) {
        int result = 0;
        result += userRepository.registerUser(user);
        result += userRepository.registerUserRole(user);

        //result = userRepository.registerUser() == userRepository.registerUserRole()
        if (result != 2) {
            throw new RuntimeException();
        }
    }

    public boolean isDuplicateUser(String email) {
        User user = userRepository.identifyUser(email);
        return user != null;
    }

    public void setUserList(Model model) {
    	LinkedList<User> userList = userRepository.getAllUser();
    	model.addAttribute("userLst", userList);
    }
}

