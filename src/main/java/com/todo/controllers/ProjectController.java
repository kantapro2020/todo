package com.todo.controllers;

import java.util.Date;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.beans.Project;
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
  @GetMapping("/projectList")
  public String getProjectList(Model model) {
      LinkedList<Project> projects = projectRepository.getProjectList(3);
      model.addAttribute("projects",projects);
      return "project_list";

  }
//  プロジェクト登録
 @GetMapping("/registerProject")
 public String registerProjectPage(Model model) {
      projectService.setUserList(model,1);
      projectService.setProjectList(model, 1);
      LinkedList<Integer> user_ids = new LinkedList<Integer>();
      model.addAttribute("projectBean", new Project());
      model.addAttribute("user_ids", user_ids);
     return"register_project";
  }

@PostMapping("/registerProject")
public String registerProject(@ModelAttribute Project project,@Param("user_list") int[] user_list,Model model) {
//    if(project==null) {
//        return "redirect:/registerProject";
//    }
    Date now= new Date();
    project.setCreated_at(now);
    project.setCompany_id(1);
    projectRepository.registerProject(project);

    for(int user_id:user_list) {
        projectRepository.registerUserProject(project.getId(), user_id);
        System.out.print(project.getId());
        System.out.print(user_id);
    }

     return "project_list";
 }
}
