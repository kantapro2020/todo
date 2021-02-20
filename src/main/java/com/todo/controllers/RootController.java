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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.beans.Project;
import com.todo.beans.Task;
import com.todo.repositories.MiniProjectRepository;
import com.todo.repositories.ProjectRepository;
import com.todo.repositories.TaskRepository;
import com.todo.services.MiniProjectService;
import com.todo.services.TaskService;

@Controller
public class RootController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    MiniProjectRepository miniProjectRepository;
    @Autowired
    TaskService taskService;
    @Autowired
    MiniProjectService miniProjectService;
//    蛭間記述
    @Autowired
    ProjectRepository projectRepository;

    @RequestMapping("/")
    public String root(Model model) {
        miniProjectService.setMiniProjectList(model, 1);
        return "miniProjectList";
    }

    @GetMapping("/{project_id}/project_detail/{mini_project_id}")
    public String projectDetail(Model model, @PathVariable("project_id") int project_id,@PathVariable("mini_project_id") int mini_project_id) {
        taskService.setTaskList(model, mini_project_id);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        return "project_detail";
    }

    @GetMapping("/{project_id}/{mini_project_id}/registerTask")
    public String registerTaskPage(Model model,  Task task, @PathVariable("mini_project_id") int mimi_project_id, @PathVariable("project_id") int project_id ) {
        miniProjectService.setMiniProject(model, mimi_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        model.addAttribute("taskBean", task);
        model.addAttribute("now", new Date());
        return "register_task";
    }

    @PostMapping("/{project_id}//{mini_project_id}/registerTask")
    public String registerTask(@Validated Task task, BindingResult bindingResult ,Model model,@PathVariable("project_id")  int project_id,@PathVariable("mini_project_id")  int mimi_project_id) {
        taskRepository.registerTask(task);
//        taskService.setTaskList(model, mimi_project_id);
        return "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @GetMapping("/{project_id}/{mini_project_id}/updateTask/{task_id}")
    public String updateTaskPage(@PathVariable("task_id") int task_id, @PathVariable("project_id") int project_id ,@PathVariable("mini_project_id") int mini_project_id , Model model) {
        taskService.setTask(model, task_id);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        return "update_task";
    }

    @PostMapping("/{project_id}/{mini_project_id}//updateTask/{task_id}")
    public String updateTask(@PathVariable("project_id") int project_id,@PathVariable("mini_project_id") int mini_project_id, @PathVariable("task_id") int task_id,@Validated Task task ,BindingResult bindingResult,  Model model) {
        taskRepository.updateTask(task);
//        taskService.setTaskList(model, mini_project_id);
//        miniProjectService.setMiniProject(model, mini_project_id);
        return "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @PostMapping("/{project_id}/deleteTask/{mini_project_id}/{task_id}")
    public String deleteTask(@PathVariable("task_id") int task_id,  @PathVariable int mini_project_id, Model model) {
        taskRepository.deleteTask(task_id);
        return  "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @PostMapping("/searchTask/{mini_project_id}")
    public String searchTask(@PathVariable("mini_project_id") int mini_project_id,
                                            @Param("keyword") String keyword,
                                            @Param("status")int status,
                                            @Param("priority")int priority,
                                            @Param("sort")String sort,
                                            @Param("order")String order,
                                             Model model) {
        int project_id = 1;
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        taskService.setSerchedTaskList(model, keyword, status, priority, mini_project_id, sort, order);
        return "project_detail";
    }

    @GetMapping("/miniProjectList/{project_id}")
    public String miniProjectList(Model model) {
        miniProjectService.setMiniProjectList(model, 1);
        return "miniProjectList";
    }

    @PostMapping("{project_id}/deleteMiniProject/{mini_project_id}")
    public String deleteMiniProject(Model model, @PathVariable("project_id") int project_id, @PathVariable("mini_project_id") int mini_project_id) {
        return "redirect:/miniProjectList/{project_id}";
    }

    @GetMapping("/registerProject")
    public String getProjectList(Model model) {
    	LinkedList<Project> projects = projectRepository.getProjectList(1);
        model.addAttribute("projects",projects);
    	return "project_list";
    }

    @PostMapping("/registerProject")
    public String registerProject(@ModelAttribute Project project, Model model) {
        projectRepository.registerProject(project);
        LinkedList<Project> projects = projectRepository.getProjectList(1);
        model.addAttribute("project", projects);
        return "project_list";
    }
}

