package com.todo.repositories;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.MiniProject;

@Mapper
public interface MiniProjectRepository {
    public  LinkedList<MiniProject> getMiniProjectList(int project_id);
    public  MiniProject getMiniProject(int mini_project_id);
}
