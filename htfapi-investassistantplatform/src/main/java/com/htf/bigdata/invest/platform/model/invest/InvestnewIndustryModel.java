package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;

public class InvestnewIndustryModel implements Serializable {
    private Long index_uni_code;

    private String index_code;

    private String abc_code;

    private String sec_name;

    private String sec_fname;

    private Long sec_uni_code;

    private String synonym;

    private static final long serialVersionUID = 1L;

    public InvestnewIndustryModel(Long index_uni_code, String index_code, String abc_code, String sec_name, String sec_fname, Long sec_uni_code, String synonym) {
        this.index_uni_code = index_uni_code;
        this.index_code = index_code;
        this.abc_code = abc_code;
        this.sec_name = sec_name;
        this.sec_fname = sec_fname;
        this.sec_uni_code = sec_uni_code;
        this.synonym = synonym;
    }

    public InvestnewIndustryModel() {
        super();
    }

    public Long getIndex_uni_code() {
        return index_uni_code;
    }

    public void setIndex_uni_code(Long index_uni_code) {
        this.index_uni_code = index_uni_code;
    }

    public String getIndex_code() {
        return index_code;
    }

    public void setIndex_code(String index_code) {
        this.index_code = index_code == null ? null : index_code.trim();
    }

    public String getAbc_code() {
        return abc_code;
    }

    public void setAbc_code(String abc_code) {
        this.abc_code = abc_code == null ? null : abc_code.trim();
    }

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name == null ? null : sec_name.trim();
    }

    public String getSec_fname() {
        return sec_fname;
    }

    public void setSec_fname(String sec_fname) {
        this.sec_fname = sec_fname == null ? null : sec_fname.trim();
    }

    public Long getSec_uni_code() {
        return sec_uni_code;
    }

    public void setSec_uni_code(Long sec_uni_code) {
        this.sec_uni_code = sec_uni_code;
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
        sb.append(", index_uni_code=").append(index_uni_code);
        sb.append(", index_code=").append(index_code);
        sb.append(", abc_code=").append(abc_code);
        sb.append(", sec_name=").append(sec_name);
        sb.append(", sec_fname=").append(sec_fname);
        sb.append(", sec_uni_code=").append(sec_uni_code);
        sb.append(", synonym=").append(synonym);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}