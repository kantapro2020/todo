package com.todo.controllers;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.beans.MiniProject;
import com.todo.beans.User;
import com.todo.repositories.MiniProjectRepository;
import com.todo.repositories.ProjectRepository;
import com.todo.services.MiniProjectService;
import com.todo.services.ProjectService;

@Controller
public class MiniProjectController {
    @Autowired
    MiniProjectService miniProjectService;

    @Autowired
    MiniProjectRepository miniProjectRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/miniProjectList/{project_id}")
    public String miniProjectList(@AuthenticationPrincipal User user,Model model, @PathVariable("project_id") int project_id) {
        model.addAttribute("user", user);
        projectService.setProject(model, project_id);
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
        projectService.setProject(model, project_id);
        miniProjectService.setSearchedMiniProjectList(model, keyword, user_id, project_id,sort, order);
        return "miniProjectList";
    }
}
