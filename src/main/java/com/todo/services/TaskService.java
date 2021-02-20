package com.todo.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.todo.beans.Task;
import com.todo.repositories.MiniProjectRepository;
import com.todo.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    MiniProjectRepository miniProjectRepository;

    public void setTask(Model model, int task_id) {
        Task task = taskRepository.getTask(task_id);
        model.addAttribute("task", task);
    }

    public void setTaskList( Model model,  int mini_project_id) {
        String keyword ="";
        int status = 100;
        int priority = 100;
        String sort ="id";
        String order = "Asc";
        LinkedList<Task> taskList = taskRepository.getTaskList(keyword, status, priority , mini_project_id, sort, order);
        model.addAttribute("taskList", taskList);
    }

    public void setSerchedTaskList( Model model,String keyword, int status, int priority,  int mini_project_id, String sort, String order) {
        LinkedList<Task> taskList = taskRepository.getTaskList(keyword, status, priority , mini_project_id, sort, order);
        model.addAttribute("taskList", taskList);
    }

    public List<Task> checkTaskList(int mini_project_id){
        String keyword ="";
        int status = 100;
        int priority = 100;
        String sort ="id";
        String order = "Asc";
        LinkedList<Task> taskList = taskRepository.getTaskList(keyword, status, priority , mini_project_id, sort, order);
        return taskList;
    }




}
