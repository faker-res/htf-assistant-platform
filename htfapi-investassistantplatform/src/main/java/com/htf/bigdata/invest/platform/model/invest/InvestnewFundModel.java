package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewFundModel implements Serializable {
    private Long org_uni_code;

    private String org_name;

    private String org_sname;

    private String synonym;

    private static final long serialVersionUID = 1L;

    public InvestnewFundModel(Long org_uni_code, String org_name, String org_sname) {
        this.org_uni_code = org_uni_code;
        this.org_name = org_name;
        this.org_sname = org_sname;
    }

    public InvestnewFundModel(Long org_uni_code, String org_name, String org_sname, String synonym) {
        this.org_uni_code = org_uni_code;
        this.org_name = org_name;
        this.org_sname = org_sname;
        this.synonym = synonym;
    }

    public InvestnewFundModel() {
        super();
    }

    public Long getOrg_uni_code() {
        return org_uni_code;
    }

    public void setOrg_uni_code(Long org_uni_code) {
        this.org_uni_code = org_uni_code;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name == null ? null : org_name.trim();
    }

    public String getOrg_sname() {
        return org_sname;
    }

    public void setOrg_sname(String org_sname) {
        this.org_sname = org_sname == null ? null : org_sname.trim();
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym == null ? null : synonym.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", org_uni_code=").append(org_uni_code);
        sb.append(", org_name=").append(org_name);
        sb.append(", org_sname=").append(org_sname);
        sb.append(", synonym=").append(synonym);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}