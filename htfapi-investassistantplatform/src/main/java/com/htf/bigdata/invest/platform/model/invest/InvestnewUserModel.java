package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;
import java.util.Date;

public class InvestnewUserModel implements Serializable {
    private Integer id;

    private String user_id;

    private Long company_id;

    private String company_type;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private String nickname;

    private String avatar;

    private String status;

    private String department;

    private String position;

    private Date create_time;

    private Date update_time;

    private static final long serialVersionUID = 1L;

    public InvestnewUserModel(Integer id, String user_id, Long company_id, String company_type, String username, String password, String email, String mobile, String nickname, String avatar, String status, String department, String position, Date create_time, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.company_id = company_id;
        this.company_type = company_type;
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.nickname = nickname;
        this.avatar = avatar;
        this.status = status;
        this.department = department;
        this.position = position;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public InvestnewUserModel() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type == null ? null : company_type.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", user_id=").append(user_id);
        sb.append(", company_id=").append(company_id);
        sb.append(", company_type=").append(company_type);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", mobile=").append(mobile);
        sb.append(", nickname=").append(nickname);
        sb.append(", avatar=").append(avatar);
        sb.append(", status=").append(status);
        sb.append(", department=").append(department);
        sb.append(", position=").append(position);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}