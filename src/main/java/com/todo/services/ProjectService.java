package com.todo.services;

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
		Project project =  projectRepository.getProject(1);
		model.addAttribute("project", project);
	}

	public void setProjectList(Model model, int company_id) {
		LinkedList<Project> projects = projectRepository.getProjectList(company_id);
		model.addAttribute("projects", projects);
	}

	public void setUserList(Model model) {
		LinkedList<User> users = userRepository.getUserListByCompanyId(1);
		for (User user :users) {
			System.out.println(user.getId());
		}
		model.addAttribute("users", users);
	}
}
