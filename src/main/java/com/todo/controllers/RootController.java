package com.todo.controllers;

import java.util.Date;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.beans.Task;
import com.todo.repositories.TaskRepository;

@Controller
public class RootController {

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/")
    public String root(Model model) {
        LinkedList<Task> result = taskRepository.getTaskList("", "id", "Asc");
        model.addAttribute("result", result);
        return "index";
    }

    @GetMapping("/registerTask")
    public String task(Model model) {
        model.addAttribute("taskBean", new Task());
        model.addAttribute("now", new Date());
        return "register_task";
    }

    @PostMapping("/registerTask")
    public String registerTask(@ModelAttribute Task task) {
        taskRepository.registerTask(task);
        return "project_detail";
    }

    @GetMapping("/updateTask/{id}")
    public String updateTaskPage(@PathVariable int  id, Model model) {
        Task task = taskRepository.getTask(id);
        model.addAttribute("taskBean", task);
        model.addAttribute("msg", "Hello");
        return "update_task";
    }

    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable int  id, @ModelAttribute Task task) {
        System.out.println(task.getId());
        taskRepository.updateTask(task);
        return "index";
    }

    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id) {
        taskRepository.deleteTask(id);
        return "index";
    }

    @PostMapping("/searchTask")
    public String searchTask(@Param("keyword") String keyword,
                                            @Param("sort")String sort,
                                            @Param("order")String order,
                                             Model model) {
        LinkedList<Task> result = taskRepository.getTaskList(keyword, sort, order);
        model.addAttribute("result",result);
        return "index";
    }
}
