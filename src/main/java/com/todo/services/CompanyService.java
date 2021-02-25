package com.todo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.todo.beans.Company;
import com.todo.repositories.CompanyRepository;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public void setCompany(Model model, int company_id) {
        Company company = companyRepository.getCompany(company_id);
        model.addAttribute("company" ,company);
    }
}
