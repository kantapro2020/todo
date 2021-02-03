package com.todo.repositories;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.Project;

@Mapper
public interface ProjectRepository {

	public int registerProject(Project project);
	public Project getProject(int id);
}
