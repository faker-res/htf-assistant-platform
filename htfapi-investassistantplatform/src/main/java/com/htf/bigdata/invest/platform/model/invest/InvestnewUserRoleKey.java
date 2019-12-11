package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewUserRoleKey implements Serializable {
    private String user_id;

    private Integer role_id;

    private static final long serialVersionUID = 1L;

    public InvestnewUserRoleKey(String user_id, Integer role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public InvestnewUserRoleKey() {
        super();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", user_id=").append(user_id);
        sb.append(", role_id=").append(role_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}