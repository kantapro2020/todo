package com.todo.repositories;


import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.Project;
import com.todo.beans.User;


@Mapper
public interface ProjectRepository {

	public int registerProject(Project project);
	public void registerUserProject(int project_id,int user_id);
	public int updateProject(Project project);
	public void updateUserProject(int project_id, int user_id);
	public void deleteProject(int project_id);
	public void deleteUserProject(int project_id);
	public Project getProject(int project_id);
	public LinkedList<Project> getProjectList(int company_id);
	public LinkedList<Integer> getProjectUsersId(int project_id);
	public LinkedList<User> getUserList(int id);
}
