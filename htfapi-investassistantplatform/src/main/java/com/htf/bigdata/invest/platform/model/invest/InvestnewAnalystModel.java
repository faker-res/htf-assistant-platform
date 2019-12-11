package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewAnalystModel implements Serializable {
    private String peo_uni_code;

    private String name;

    private String analyst_code;

    private Long org_uni_code;

    private String org_sname;

    private static final long serialVersionUID = 1L;

    public InvestnewAnalystModel(String peo_uni_code, String name, String analyst_code, Long org_uni_code, String org_sname) {
        this.peo_uni_code = peo_uni_code;
        this.name = name;
        this.analyst_code = analyst_code;
        this.org_uni_code = org_uni_code;
        this.org_sname = org_sname;
    }

    public InvestnewAnalystModel() {
        super();
    }

    public String getPeo_uni_code() {
        return peo_uni_code;
    }

    public void setPeo_uni_code(String peo_uni_code) {
        this.peo_uni_code = peo_uni_code == null ? null : peo_uni_code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAnalyst_code() {
        return analyst_code;
    }

    public void setAnalyst_code(String analyst_code) {
        this.analyst_code = analyst_code == null ? null : analyst_code.trim();
    }

    public Long getOrg_uni_code() {
        return org_uni_code;
    }

    public void setOrg_uni_code(Long org_uni_code) {
        this.org_uni_code = org_uni_code;
    }

    public String getOrg_sname() {
        return org_sname;
    }

    public void setOrg_sname(String org_sname) {
        this.org_sname = org_sname == null ? null : org_sname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", peo_uni_code=").append(peo_uni_code);
        sb.append(", name=").append(name);
        sb.append(", analyst_code=").append(analyst_code);
        sb.append(", org_uni_code=").append(org_uni_code);
        sb.append(", org_sname=").append(org_sname);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}