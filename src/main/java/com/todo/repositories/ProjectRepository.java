package com.todo.repositories;


import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.Project;
import com.todo.beans.User;
import com.todo.beans.UserProject;


@Mapper
public interface ProjectRepository {

	public int registerProject(Project project);
	public Project getProject(int id);
	public LinkedList<Project> getProjectList(int company_id);
	public LinkedList<User> getUserList(int id);
}
