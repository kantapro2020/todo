package com.todo.repositories;

import org.apache.ibatis.annotations.Mapper;

import com.todo.beans.Company;

@Mapper
public interface CompanyRepository {
    public Company getCompany(int company_id);
}
