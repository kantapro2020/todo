package com.todo.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.todo.beans.MiniProject;
import com.todo.repositories.MiniProjectRepository;
import com.todo.repositories.TaskRepository;

@Service
public class MiniProjectService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    MiniProjectRepository miniProjectRepository;

    public void setMiniProject(Model model, int mini_project_id) {
        MiniProject mini_project = miniProjectRepository.getMiniProject(mini_project_id);
        model.addAttribute("mini_project", mini_project);
    }

    public void setMiniProjectList(Model model, int project_id) {
       int  user_id = 100;
       String sort = "id";
       String order = "Asc";
        LinkedList<MiniProject> mini_projects =  miniProjectRepository.getMiniProjectList("", user_id, project_id, sort, order);
        model.addAttribute("mini_projects", mini_projects);
    }

    public void setSearchedMiniProjectList(Model model,String keyword, int user_id, int project_id, String sort, String order) {
        project_id = 1;
        LinkedList<MiniProject> mini_projects =  miniProjectRepository.getMiniProjectList(keyword, user_id, project_id, sort, order);
        model.addAttribute("mini_projects", mini_projects);
    }
}
