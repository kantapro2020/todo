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

import com.todo.beans.Project;
import com.todo.beans.Task;
import com.todo.repositories.ProjectRepository;
import com.todo.repositories.TaskRepository;

@Controller
public class RootController {

    @Autowired
    TaskRepository taskRepository;
//    蛭間記述
    @Autowired
    ProjectRepository projectRepository;

    @RequestMapping("/")
    public String root(Model model) {
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , "id", "Asc");
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
    public String registerTask(@ModelAttribute Task task, Model model) {
        taskRepository.registerTask(task);
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , "id", "Asc");
        model.addAttribute("result", result);
        return "index";
    }

    @GetMapping("/updateTask/{id}")
    public String updateTaskPage(@PathVariable int  id, Model model) {
        Task task = taskRepository.getTask(id);
        model.addAttribute("taskBean", task);
        model.addAttribute("msg", "Hello");
        return "update_task";
    }

    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable int  id, @ModelAttribute Task task, Model model) {
        taskRepository.updateTask(task);
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , "id", "Asc");
        model.addAttribute("result", result);
        return "index";
    }

    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id, Model model) {
        taskRepository.deleteTask(id);
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , "id", "Asc");
        model.addAttribute("result", result);
        return "index";
    }

    @PostMapping("/searchTask")
    public String searchTask(@Param("keyword") String keyword,
                                            @Param("status")int status,
                                            @Param("priority")int priority,
                                            @Param("sort")String sort,
                                            @Param("order")String order,
                                             Model model) {
        LinkedList<Task> result = taskRepository.getTaskList(keyword, status , priority , sort, order);
        model.addAttribute("result",result);
        return "index";
    }


    @GetMapping("/registerProject")
    public String getProjectList(Model model) {
    	LinkedList<Project> projects = projectRepository.getProjectList(1);
        model.addAttribute("projects",projects);
    	return "project_list";
    }
//    public String registerProject(Model model) {
//        model.addAttribute("projectBean", new Project());
//        model.addAttribute("now", new Date());
//        return "project_list";
//    }
    

    @PostMapping("/registerProject")
    public String registerProject(@ModelAttribute Project project, Model model) {
        projectRepository.registerProject(project);
        LinkedList<Project> projects = projectRepository.getProjectList(1);
        model.addAttribute("project", projects);
        return "project_list";
    }


    
}