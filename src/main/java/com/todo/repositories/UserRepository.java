package com.todo.repositories;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.User;



@Mapper
public interface UserRepository {
    public User identifyUser(String email);

    public int registerUser(User user);

    public int registerUserRole(User user);

    public LinkedList<User> getAllUser();

    public User getUser(int id);
    
    public LinkedList<User> getUsers();

}