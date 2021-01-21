package com.todo.repositories;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.Task;

@Mapper
public interface TaskRepository {
    public int registerTask(Task task);
    public Task getTask(int id);
    public LinkedList<Task> getTaskList(String keyword, String sort, String order);
    public int updateTask(Task task);
    public int deleteTask(int id);
}
