package com.todo.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todo.beans.MiniProject;
import com.todo.beans.Project;
import com.todo.beans.Task;
import com.todo.beans.User;

import com.todo.beans.UserProject;

import com.todo.beans.UserRegistrationBean;
import com.todo.repositories.MiniProjectRepository;
import com.todo.repositories.ProjectRepository;
import com.todo.repositories.TaskRepository;
import com.todo.repositories.UserProjectRepository;
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

    @PostMapping("/logout")
    public String logout() {
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


    @RequestMapping("/")
    public String root(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("user", user);
        miniProjectService.setMiniProjectList(model, 1);
        return "index";
    }

    @GetMapping("/miniProjectList/{project_id}")
    public String miniProjectList(@AuthenticationPrincipal User user,Model model, @PathVariable("project_id") int project_id) {
        model.addAttribute("user", user);
        Project project =projectRepository.getProject(project_id);
        model.addAttribute(project);
        miniProjectService.setMiniProjectList(model, project_id);
        return "miniProjectList";
    }
    @GetMapping("{project_id}/registerMiniProject")
    public String registerMiniProjectPage(@AuthenticationPrincipal User user,@ModelAttribute MiniProject mini_project,@PathVariable("project_id") int project_id,Model model) {
        model.addAttribute("user", user);
        model.addAttribute("project_id", project_id);
        model.addAttribute("mini_project" ,mini_project);
        miniProjectService.setMiniProjectList(model, project_id);
        return "register_mini_project";
    }

    @PostMapping("{project_id}/registerMiniProject")
    public String registerMiniProject(@AuthenticationPrincipal User user,@Validated MiniProject mini_project, BindingResult bindingResult,@PathVariable("project_id") int project_id,Model model) {
        model.addAttribute("user", user);
        miniProjectRepository.registerMiniProject(mini_project);
        if(bindingResult.hasErrors()) {
            return "redirect:/{project_id}/registerMiniProject";
        }
        return "redirect:/miniProjectList/{project_id}";
    }

    @GetMapping("{project_id}/updateMiniProject/{mini_project_id}")
    public String updateMiniProjectPage(@AuthenticationPrincipal User user,@PathVariable("project_id") int project_id, @PathVariable("mini_project_id") int mini_project_id, Model model) {
        model.addAttribute("user", user);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        return "update_mini_project";
    }

    @PostMapping("{project_id}/updateMiniProject/{mini_project_id}")
    public String updateMiniProject(@AuthenticationPrincipal User user,@Validated MiniProject mini_project, BindingResult bindingResult,@PathVariable("project_id") int project_id,@PathVariable("mini_project_id") int mini_project_id, Model model) {
        model.addAttribute("user", user);
        miniProjectRepository.updateMiniProject(mini_project);
        if(bindingResult.hasErrors()) {
            return "redirect:{project_id}/updateMiniProject/{mini_project_id}";
        }
        miniProjectService.setMiniProject(model, mini_project_id);
        return "redirect:/miniProjectList/{project_id}";
    }

    @PostMapping("{project_id}/deleteMiniProject/{mini_project_id}")
    public String deleteMiniProject(@AuthenticationPrincipal User user,Model model, @PathVariable("project_id") int project_id, @PathVariable("mini_project_id") int mini_project_id) {
        model.addAttribute("user", user);
        miniProjectRepository.deleteMiniProject(mini_project_id);
        return "redirect:/miniProjectList/{project_id}";
    }

    @PostMapping("/searchMiniProject/{project_id}")
    public String searchMiniProject(@AuthenticationPrincipal User user,
                                                        @PathVariable("project_id") int project_id,
                                                        Model model,
                                                        @Param("keyword") String keyword,
                                                        @Param("user_id")int user_id,
                                                        @Param("sort")String sort,
                                                        @Param("order")String order) {
        model.addAttribute("user", user);
        Project project =projectRepository.getProject(project_id);
        model.addAttribute(project);
        miniProjectService.setSearchedMiniProjectList(model, keyword, user_id, project_id,sort, order);
        return "miniProjectList";
    }

    @GetMapping("/{project_id}/project_detail/{mini_project_id}")
    public String projectDetail(@AuthenticationPrincipal User user,Model model, @PathVariable("project_id") int project_id,@PathVariable("mini_project_id") int mini_project_id) {
        model.addAttribute("user", user);
        Project project =projectRepository.getProject(project_id);
        model.addAttribute("project", project);
        taskService.setTaskList(model, mini_project_id);
        miniProjectService.setMiniProject(model, mini_project_id);
        miniProjectService.setMiniProjectList(model, project_id);
        return "project_detail";
    }

    @GetMapping("/{project_id}/{mini_project_id}/registerTask")
    public String registerTaskPage(@AuthenticationPrincipal User user,Model model,  Task task, @PathVariable("mini_project_id") int mimi_project_id, @PathVariable("project_id") int project_id ) {
        model.addAttribute("user", user);
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
//        taskService.setTaskList(model, mimi_project_id);
        return "redirect:/{project_id}/project_detail/{mini_project_id}";
    }

    @GetMapping("/{project_id}/{mini_project_id}/updateTask/{task_id}")
    public String updateTaskPage(@AuthenticationPrincipal User user,@PathVariable("task_id") int task_id, @PathVariable("project_id") int project_id ,@PathVariable("mini_project_id") int mini_project_id , Model model) {
        model.addAttribute("user", user);
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
        model.addAttribute("user", user);
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
        model.addAttribute("user", user);
       Project  project = projectRepository.getProject(project_id);
       model.addAttribute("project", project);
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


//    蛭間記載
//    プロジェクト一覧
    @GetMapping("/projectList")
    public String getProjectList(Model model) {
    	LinkedList<Project> projects = projectRepository.getProjectList(3);
        model.addAttribute("projects",projects);
    	return "project_list";

    }
//    プロジェクト登録
   @GetMapping("/registerProject")
   public String registerProjectPage(Model model) {
	    projectService.setUserList(model);
	    projectService.setProject(model, 1);
	    projectService.setProjectList(model, 1);
    	model.addAttribute("projectBean", new Project());
    	model.addAttribute("now", new Date());
	   return"register_project";
    }

  @PostMapping("/registerProject")
  public String registerProject(@ModelAttribute Project project, Model model) {
	  projectRepository.registerProject(project);

      LinkedList<Integer> users = project.getUserList();
       for(int user: users) {
    	  projectRepository.registerUserProject(user, project.getId());
       }
       return "project_list";
   }
}
