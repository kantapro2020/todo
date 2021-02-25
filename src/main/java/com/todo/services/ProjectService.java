package com.todo.services;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.todo.beans.Project;
import com.todo.beans.User;
import com.todo.repositories.ProjectRepository;
import com.todo.repositories.UserRepository;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;

	public void setProject(Model model, int id) {
		Project project =  projectRepository.getProject(id);
		System.out.println(project.getId());
		model.addAttribute("project", project);
	}

	public void setProjectList(Model model, int company_id) {
		LinkedList<Project> projects = projectRepository.getProjectList(company_id);
		model.addAttribute("projects", projects);
	}

	public void setUserList(Model model, int conpany_id) {
		LinkedList<User> users = userRepository.getUserListByCompanyId(conpany_id);
		model.addAttribute("users", users);
	}

	public void registerProject(Project project, int company_id) {
	    Date now= new Date();
        project.setCreated_at(now);
        project.setCompany_id(company_id);
        projectRepository.registerProject(project);
	}

	public void registerUserProject(Project project) {
	    for(int user_id: project.getUser_list()) {
            projectRepository.registerUserProject(project.getId(), user_id);
        }
	}
}
