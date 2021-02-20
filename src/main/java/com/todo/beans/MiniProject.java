package com.todo.beans;

import java.io.Serializable;

public class MiniProject implements Serializable {
    private static final long serialVersionUID = 42L;
    private int id;
    private String mini_project_title;
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
}
