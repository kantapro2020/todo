package com.todo.beans;

import java.io.Serializable;

public class MiniProject implements Serializable {
    private static final long serialVersionUID = 42L;
    private int id;
    private String mini_project_title;
    private int project_id;
    private String project_title;
    private int user_id;
    private String username;
    /**
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id セットする id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return mini_project_name
     */
    public String getMini_project_title() {
        return mini_project_title;
    }
    /**
     * @param mini_project_name セットする mini_project_name
     */
    public void setMini_project_title(String mini_project_title) {
        this.mini_project_title = mini_project_title;
    }
    /**
     * @return project_title
     */
    public String getProject_title() {
        return project_title;
    }
    /**
     * @param project_title セットする project_title
     */
    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }
    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username セットする username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return project_id
     */
    public int getProject_id() {
        return project_id;
    }
    /**
     * @param project_id セットする project_id
     */
    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
    /**
     * @return user_id
     */
    public int getUser_id() {
        return user_id;
    }
    /**
     * @param user_id セットする user_id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
