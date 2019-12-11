package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;
import java.util.Date;

public class InvestnewMessageBroadcastModel implements Serializable {
    private Long id;

    private String sender_user_id;

    private Long company_id;

    private String company_type;

    private Boolean is_revoke;

    private Date create_time;

    private Date update_time;

    private String message;

    private static final long serialVersionUID = 1L;

    public InvestnewMessageBroadcastModel(Long id, String sender_user_id, Long company_id, String company_type, Boolean is_revoke, Date create_time, Date update_time) {
        this.id = id;
        this.sender_user_id = sender_user_id;
        this.company_id = company_id;
        this.company_type = company_type;
        this.is_revoke = is_revoke;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public InvestnewMessageBroadcastModel(Long id, String sender_user_id, Long company_id, String company_type, Boolean is_revoke, Date create_time, Date update_time, String message) {
        this.id = id;
        this.sender_user_id = sender_user_id;
        this.company_id = company_id;
        this.company_type = company_type;
        this.is_revoke = is_revoke;
        this.create_time = create_time;
        this.update_time = update_time;
        this.message = message;
    }

    public InvestnewMessageBroadcastModel() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender_user_id() {
        return sender_user_id;
    }

    public void setSender_user_id(String sender_user_id) {
        this.sender_user_id = sender_user_id == null ? null : sender_user_id.trim();
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

    public Boolean getIs_revoke() {
        return is_revoke;
    }

    public void setIs_revoke(Boolean is_revoke) {
        this.is_revoke = is_revoke;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sender_user_id=").append(sender_user_id);
        sb.append(", company_id=").append(company_id);
        sb.append(", company_type=").append(company_type);
        sb.append(", is_revoke=").append(is_revoke);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", message=").append(message);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}