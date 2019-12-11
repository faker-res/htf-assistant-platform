package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewAccessModel implements Serializable {
    private Integer id;

    private String access_id;

    private Long company_id;

    private String parent_id;

    private String name;

    private String description;

    private static final long serialVersionUID = 1L;

    public InvestnewAccessModel(Integer id, String access_id, Long company_id, String parent_id, String name, String description) {
        this.id = id;
        this.access_id = access_id;
        this.company_id = company_id;
        this.parent_id = parent_id;
        this.name = name;
        this.description = description;
    }

    public InvestnewAccessModel() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id == null ? null : access_id.trim();
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id == null ? null : parent_id.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", access_id=").append(access_id);
        sb.append(", company_id=").append(company_id);
        sb.append(", parent_id=").append(parent_id);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}