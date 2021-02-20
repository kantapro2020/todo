package com.todo.beans;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class User implements UserDetails {

    private static final long serialVersionUID = -41353466463452342L;
    private int id;
    private String username;
    private String password;
    private int delete_flg;
    private String created_at;
    private String updated_at;
    private int company_id;
    private boolean expired;
    private boolean locked;
    private List<String> roles;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        // TODO 自動生成されたメソッド・スタブ
        return password;
    }

    @Override
    public String getUsername() {
        // TODO 自動生成されたメソッド・スタブ
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO 自動生成されたメソッド・スタブ
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO 自動生成されたメソッド・スタブ
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO 自動生成されたメソッド・スタブ
        return !expired;
    }

    @Override
    public boolean isEnabled() {
        // TODO 自動生成されたメソッド・スタブ
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param user_name セットする user_name
     */
    public void setUsername(String user_name) {
        this.username = user_name;
    }

    /**
     * @return delete_flg
     */
    public int getDelete_flg() {
        return delete_flg;
    }

    /**
     * @param delete_flg セットする delete_flg
     */
    public void setDelete_flg(int delete_flg) {
        this.delete_flg = delete_flg;
    }

    /**
     * @return created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at セットする created_at
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return updated_at
     */
    public String getUpdated_at() {
        return updated_at;
    }

    /**
     * @param updated_at セットする updated_at
     */
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * @return company_id
     */
    public int getCompany_id() {
        return company_id;
    }

    /**
     * @param company_id セットする company_id
     */
    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }
}
