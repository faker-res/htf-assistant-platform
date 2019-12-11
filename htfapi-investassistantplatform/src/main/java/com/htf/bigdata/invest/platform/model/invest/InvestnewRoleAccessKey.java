package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewRoleAccessKey implements Serializable {
    private Integer role_id;

    private String access_id;

    private static final long serialVersionUID = 1L;

    public InvestnewRoleAccessKey(Integer role_id, String access_id) {
        this.role_id = role_id;
        this.access_id = access_id;
    }

    public InvestnewRoleAccessKey() {
        super();
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id == null ? null : access_id.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", role_id=").append(role_id);
        sb.append(", id=").append(access_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}