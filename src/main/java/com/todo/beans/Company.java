package com.todo.beans;

import java.io.Serializable;

public class Company implements Serializable {
    private static final long serialVersionUID = 45L;
    int id;
    String company_name;
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
     * @return company_name
     */
    public String getCompany_name() {
        return company_name;
    }
    /**
     * @param company_name セットする company_name
     */
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }


}
