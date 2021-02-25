package com.todo.controllers;

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
import com.todo.services.CompanyService;
import com.todo.services.MiniProjectService;
import com.todo.services.UserService;

@Controller
public class RootController {
    @Autowired
    CompanyService companyService;
    @Autowired
    MiniProjectService miniProjectService;
   @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String root(@AuthenticationPrincipal User user,Model model) {
        userService.setLoginUser(model, user);
        companyService.setCompany(model,1);
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

        if (userService.isDuplicateUser(userRegistrationBean.getUsername())) {
            model.addAttribute("duplicateError", "すでに使われているユーザー名です");
            return "userRegistration";
        }
        userService.registerUser(userRegistrationBean);

        return "login";
    }
}
