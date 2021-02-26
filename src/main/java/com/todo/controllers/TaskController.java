 package com.todo.controllers;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.beans.Task;
import com.todo.beans.User;
import com.todo.repositories.ProjectRepository;
import com.todo.repositories.TaskRepository;
import com.todo.services.CompanyService;
import com.todo.services.MiniProjectService;
import com.todo.services.ProjectService;
import com.todo.services.TaskService;
import com.todo.services.UserService;




@Controller
public class TaskController  {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    MiniProjectService miniProjectService;

    @Autowired
    UserService userService;
    @Autowired
    CompanyService companyService;

    @GetMapping("/{project_id}/project_detail/{mini_project_id}")
    public String projectDetail(@AuthenticationPrincipal User user ,Model model, @PathVariable("project_id") int project_id,@PathVariable("mini_project_id") int mini_project_id) {
        userService.setLoginUser(model, user);
        companyService.setCompany(model, 1);
        System.out.println(project_id);
        projectService.setProject(model, project_id);
        taskService.setTaskList(model, mini_project_id);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        return "project_detail";
    }

    @GetMapping("/{project_id}/{mini_project_id}/registerTask")
    public String registerTaskPage(@AuthenticationPrincipal User user,Model model,  Task task, @PathVariable("mini_project_id") int mimi_project_id, @PathVariable("project_id") int project_id ) {
        userService.setLoginUser(model, user);
        companyService.setCompany(model, 1);
        miniProjectService.setMiniProject(model, mimi_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        model.addAttribute("taskBean", task);
        model.addAttribute("now", new Date());
        return "register_task";
    }

    @PostMapping("/{project_id}//{mini_project_id}/registerTask")
    public String registerTask(@AuthenticationPrincipal User user,@Validated Task task, BindingResult bindingResult ,Model model,@PathVariable("project_id")  int project_id,@PathVariable("mini_project_id")  int mimi_project_id) {
        model.addAttribute("user", user);
        taskRepository.registerTask(task);
        return "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @GetMapping("/{project_id}/{mini_project_id}/updateTask/{task_id}")
    public String updateTaskPage(@AuthenticationPrincipal User user,@PathVariable("task_id") int task_id, @PathVariable("project_id") int project_id ,@PathVariable("mini_project_id") int mini_project_id , Model model) {
        userService.setLoginUser(model, user);
        companyService.setCompany(model, 1);
        taskService.setTask(model, task_id);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        return "update_task";
    }

    @PostMapping("/{project_id}/{mini_project_id}/updateTask/{task_id}")
    public String updateTask(@AuthenticationPrincipal User user,@Validated Task task ,BindingResult bindingResult,  Model model,@PathVariable("project_id") int project_id,@PathVariable("mini_project_id") int mini_project_id, @PathVariable("task_id") int task_id) {
        model.addAttribute("user", user);
        taskRepository.updateTask(task);
        return "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @PostMapping("/{project_id}/deleteTask/{mini_project_id}/{task_id}")
    public String deleteTask(@AuthenticationPrincipal User user,@PathVariable("task_id") int task_id,  @PathVariable int mini_project_id, Model model) {
        taskRepository.deleteTask(task_id);
        return  "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @PostMapping("/{project_id}/searchTask/{mini_project_id}")
    public String searchTask(@AuthenticationPrincipal User user,
                                            @PathVariable("project_id") int project_id,
                                            @PathVariable("mini_project_id") int mini_project_id,
                                            @Param("keyword") String keyword,
                                            @Param("status")int status,
                                            @Param("priority")int priority,
                                            @Param("sort")String sort,
                                            @Param("order")String order,
                                             Model model) {
        userService.setLoginUser(model, user);
        companyService.setCompany(model, 1);
        projectService.setProject(model, project_id);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        taskService.setSerchedTaskList(model, keyword, status, priority, mini_project_id, sort, order);
        return "project_detail";
    }

}
