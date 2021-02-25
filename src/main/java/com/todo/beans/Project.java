package com.todo.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class Project implements Serializable {


	private static final long serialVersionUID = 42L;
	private int id;
	private String project_name;
	private int manager_id;
	private int company_id;
	private String created_at;
	private String user_name;
	private String company_name;
	private LinkedList<Integer> user_list;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public int getManager_id() {
		return manager_id;
	}
	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public LinkedList<Integer>  getUser_list() {
		return user_list;
	}
	public void setUser_list(LinkedList<Integer> user_list) {
		this.user_list = user_list;
	}
}
