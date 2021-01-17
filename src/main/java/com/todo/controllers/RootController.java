package com.todo.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.beans.Task;
import com.todo.repositories.TaskRepository;


@Controller
public class RootController {

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/task")
    public String task(Model model) {
        model.addAttribute("taskBean", new Task());
        model.addAttribute("now", new Date());
        return "task";
    }

    @PostMapping("/registerTask")
    public String registerTask(@ModelAttribute Task task) {
        taskRepository.registerTask(task);
        return "project_detail";
    }
}
