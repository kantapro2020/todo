package com.todo.repositories;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.Task;

@Mapper
public interface TaskRepository {
    public int registerTask(Task task);
}
