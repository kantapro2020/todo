package com.todo.repositories;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.MiniProject;

@Mapper
public interface MiniProjectRepository {
    public  LinkedList<MiniProject> getMiniProjectList(String keyword, int user_id, int project_id,String sort, String order);
    public  MiniProject getMiniProject(int mini_project_id);
    public void registerMiniProject(MiniProject miniProject);
    public  void updateMiniProject(MiniProject miniProject);
    public void deleteMiniProject(int mini_project_id);
}
