package com.todo.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.User;



@Mapper
public interface UserRepository {
    public User identifyUser(String email);

    public int registerUser(User user);

    public int registerUserRole(User user);

    public List<User> getAllUser();

    public User getUser(int id);

}