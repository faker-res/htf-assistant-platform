package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewRoleModel implements Serializable {
    private Integer role_id;

    private Long company_id;

    private String name;

    private String description;

    private String status;

    private static final long serialVersionUID = 1L;

    public InvestnewRoleModel(Integer role_id, Long company_id, String name, String description, String status) {
        this.role_id = role_id;
        this.company_id = company_id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public InvestnewRoleModel() {
        super();
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", role_id=").append(role_id);
        sb.append(", company_id=").append(company_id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}