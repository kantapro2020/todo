package com.todo.controllers;

import java.util.Date;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 ,100, "id", "Asc");
        model.addAttribute("result", result);
        return "index";
    }

    @GetMapping("project_detail")
    public String projectDetail(Model model) {
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , 1, "id", "Asc");
        model.addAttribute("result", result);
        return "project_detail";
    }

    @GetMapping("/registerTask")
    public String task(Model model,  Task task) {
        model.addAttribute("taskBean", task);
        model.addAttribute("now", new Date());
        return "register_task";
    }

    @PostMapping("/registerTask")
    public String registerTask(@Validated Task task, BindingResult bindingResult ,Model model) {
        taskRepository.registerTask(task);
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , 100, "id", "Asc");
        model.addAttribute("result", result);
        return "project_detail";
    }


    @GetMapping("/updateTask/{id}")
    public String updateTaskPage(@PathVariable int  id, Model model) {
        Task task = taskRepository.getTask(id);
        model.addAttribute("taskBean", task);
        return "update_task";
    }

    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable int  id, @Validated Task task, BindingResult bindingResult,  Model model) {

        taskRepository.updateTask(task);
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 , 100, "id", "Asc");
        model.addAttribute("result", result);
        return "project_detail";
    }

    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable int id, Model model) {
        taskRepository.deleteTask(id);
        LinkedList<Task> result = taskRepository.getTaskList("", 100, 100 ,100, "id", "Asc");
        model.addAttribute("result", result);
        return "project_detail";
    }

    @PostMapping("/searchTask")
    public String searchTask(@Param("keyword") String keyword,
                                            @Param("status")int status,
                                            @Param("priority")int priority,
                                            @Param("sort")String sort,
                                            @Param("order")String order,
                                             Model model) {
        LinkedList<Task> result = taskRepository.getTaskList(keyword, status , priority ,1, sort, order);
        model.addAttribute("result",result);
        return "project_detail";
    }


    @GetMapping("/registerProject")
    public String getProjectList(Model model) {
    	LinkedList<Project> projects = projectRepository.getProjectList(1);
        model.addAttribute("projects",projects);
    	return "project_list";
<<<<<<< Updated upstream
    }    
=======
    }
//    public String registerProject(Model model) {
//        model.addAttribute("projectBean", new Project());
//        model.addAttribute("now", new Date());
//        return "project_list";
//    }

>>>>>>> Stashed changes

    @PostMapping("/registerProject")
    public String registerProject(@ModelAttribute Project project, Model model) {
        projectRepository.registerProject(project);
        LinkedList<Project> projects = projectRepository.getProjectList(1);
        model.addAttribute("project", projects);
        return "project_list";
    }
}

<<<<<<< Updated upstream
=======


}
>>>>>>> Stashed changes
