package com.todo.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.todo.beans.User;
import com.todo.beans.UserRegistrationBean;
import com.todo.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserRegistrationBean userRegistrationBean) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String created_at = sdf.format(calendar.getTime());
        User user = new User();
        user.setUsername(userRegistrationBean.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationBean.getPassword()));
        user.setCompany_id(userRegistrationBean.getCompany_id());
        user.setCreated_at(created_at);
        userRepository.registerUser(user);
        userRepository.registerUserRole(user);
    }

    public boolean isDuplicateUser(String email) {
        User user = userRepository.identifyUser(email);
        return user != null;
    }

    public void setUserList(Model model) {
    	LinkedList<User> userList = userRepository.getAllUser();
    	model.addAttribute("userLst", userList);
    }

    public void setLoginUser(Model model, User user) {
        System.out.println(user.getCompany_id());
        model.addAttribute("user",user);
    }


}

