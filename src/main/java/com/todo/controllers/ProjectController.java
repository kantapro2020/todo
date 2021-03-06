package com.todo.controllers;


import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.beans.Project;
import com.todo.beans.User;
import com.todo.repositories.ProjectRepository;
import com.todo.services.CompanyService;
import com.todo.services.ProjectService;
import com.todo.services.UserService;

@Controller
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    CompanyService companyService;

//  蛭間記載
//  プロジェクト一覧
      @GetMapping("/{company_id}/projectList")
      public String getProjectList(@AuthenticationPrincipal User user,Model model, @PathVariable("company_id") int company_id) {
          userService.setLoginUser(model, user);
          companyService.setCompany(model, company_id);
          projectService.setProjectList(model, company_id);
          return "project_list";
      }

//  プロジェクト登録
     @GetMapping("/{company_id}/registerProject")
     public String registerProjectPage(@AuthenticationPrincipal User user,Model model,
                                                             @PathVariable("company_id")int company_id) {
         userService.setLoginUser(model, user);
         companyService.setCompany(model, company_id);
          projectService.setUserList(model,company_id);
          projectService.setProjectList(model, company_id);
          model.addAttribute("projectBean", new Project());
         return"register_project";
      }

    @PostMapping("/{company_id}/registerProject")
    public String registerProject(@Valid Project project,
                                                  @PathVariable("company_id")int company_id,
                                                  Model model) {
            projectService.registerProject(project, company_id);
            projectService.registerUserProject(project);
            return "redirect:/{company_id}/projectList";
        }
    @GetMapping("/{company_id}/updateProject/{project_id}")
    public String updateProjectPage(@AuthenticationPrincipal User user,
                                                         @ModelAttribute Project project,
                                                         @PathVariable("company_id")int company_id,
                                                         Model model) {
    	model.addAttribute("user", user);
    	projectService.setProject(model,project.getId());
        projectService.setUserList(model,company_id);
        projectService.setProjectList(model, company_id);
        return "update_project";
    }
    @PostMapping("/{company_id}/updateProject/{project_id}")
    public String updateProject(@AuthenticationPrincipal User user,
                                                @ModelAttribute Project project,
                                                @PathVariable("company_id")int company_id,
            Model model) {
    	Date now= new Date();
    	project.setUpdated_at(now);
        project.setCompany_id(company_id);
        projectRepository.updateProject(project);
    	for(int user_id: project.getUser_list()) {
            projectRepository.updateUserProject(project.getId(), user_id);
        }
    	return "redirect:/{company_id}/projectList";
    }

    @PostMapping("/{company_id}/deleteProject/{project_id}")
    public String deleteProject(@PathVariable("company_id")int company_id, @PathVariable("project_id")int project_id) {
    	projectRepository.deleteUserProject(project_id);
    	projectRepository.deleteProject(project_id);

        return "redirect:/{company_id}/projectList";
    }
}
