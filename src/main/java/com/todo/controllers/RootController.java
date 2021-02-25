package com.todo.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.beans.User;
import com.todo.beans.UserRegistrationBean;
import com.todo.repositories.MiniProjectRepository;
import com.todo.repositories.ProjectRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserRepository;
import com.todo.services.GetUserDataService;
import com.todo.services.MiniProjectService;
import com.todo.services.ProjectService;
import com.todo.services.TaskService;
import com.todo.services.UserRegistrationService;

@Controller
public class RootController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    MiniProjectRepository miniProjectRepository;
//    蛭間記述
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;
    @Autowired
    MiniProjectService miniProjectService;
    @Autowired
    UserRegistrationService userRegistrationService;
    @Autowired
    GetUserDataService getUserDataService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String root(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("user", user);
        miniProjectService.setMiniProjectList(model, 1);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser( ) {
        return "redirect:/miniProjectList/1";
    }

    @PostMapping("/login-error")
    public String loginError(@RequestAttribute("SPRING_SECURITY_LAST_EXCEPTION") AuthenticationException ex,
            Model model) {
        model.addAttribute("authenticationException", ex);
        return "login";
    }

    @GetMapping("/userRegistration")
    public String userRegistration(Model model) {
        model.addAttribute("userRegistrationBean", new UserRegistrationBean());
        return "userRegistration";
    }

    @PostMapping("/userRegistration")
    public String registrationUser(@Valid  UserRegistrationBean userRegistrationBean,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            return "userRegistration";
        }

        if (userRegistrationService.isDuplicateUser(userRegistrationBean.getUsername())) {
            model.addAttribute("duplicateError", "すでに使われているユーザー名です");
            return "userRegistration";
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String created_at = sdf.format(calendar.getTime());

        User user = new User();
        user.setUsername(userRegistrationBean.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationBean.getPassword()));
        user.setCompany_id(userRegistrationBean.getCompany_id());
        user.setCreated_at(created_at);
        userRegistrationService.registerUser(user);

        return "login";
    }
}
