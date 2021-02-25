package com.todo.controllers;

import java.util.Date;
import java.util.LinkedList;

import javax.websocket.server.PathParam;

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
import com.todo.services.ProjectService;

@Controller
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

//  蛭間記載
//  プロジェクト一覧
  @GetMapping("/{company_id}/projectList")
  public String getProjectList(@AuthenticationPrincipal User user,Model model, @PathVariable("company_id") int company_id) {
      model.addAttribute("user", user);
      LinkedList<Project> projects = projectRepository.getProjectList(company_id);
      model.addAttribute("projects",projects);
      return "project_list";

  }
//  プロジェクト登録
 @GetMapping("/{company_id}/registerProject")
 public String registerProjectPage(@AuthenticationPrincipal User user,Model model,
                                                         @PathVariable("company_id")int company_id) {
     model.addAttribute("user", user);
      projectService.setUserList(model,company_id);
      projectService.setProjectList(model, company_id);
      model.addAttribute("projectBean", new Project());
     return"register_project";
  }

@PostMapping("/{company_id}/registerProject")
public String registerProject(@ModelAttribute Project project,
        @PathVariable("company_id")int company_id,
        Model model) {
        if(project==null) {
            return "redirect:/{company_id}/registerProject";
        }
        Date now= new Date();
        project.setCreated_at(now);
        project.setCompany_id(1);
        projectRepository.registerProject(project);

        for(int user_id: project.getUser_list()) {
            projectRepository.registerUserProject(project.getId(), user_id);
            System.out.print(project.getId());
            System.out.print(user_id);
        }
             return "redirect:/{company_id}/projectList";
        }

@PostMapping("/{company_id}/deleteProject/{project_id}")
public String deleteProject(@PathParam("company_id")int company_id, @PathParam("project_id")int project_id) {
    projectRepository.deleteProject(project_id);
    projectRepository.deleteUserProject(project_id);
    return "redirect:/{company_id}/projectList";
}

}
