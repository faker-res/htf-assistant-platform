package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;
import java.util.Date;

public class InvestnewFeedbackModel implements Serializable {
    private Long id;

    private String user_id;

    private String title;

    private String contact_info;

    private String contacts;

    private Date finish_time;

    private Date create_time;

    private Date update_time;

    private String content;

    private static final long serialVersionUID = 1L;

    public InvestnewFeedbackModel(Long id, String user_id, String title, String contact_info, String contacts, Date finish_time, Date create_time, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.contact_info = contact_info;
        this.contacts = contacts;
        this.finish_time = finish_time;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public InvestnewFeedbackModel(Long id, String user_id, String title, String contact_info, String contacts, Date finish_time, Date create_time, Date update_time, String content) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.contact_info = contact_info;
        this.contacts = contacts;
        this.finish_time = finish_time;
        this.create_time = create_time;
        this.update_time = update_time;
        this.content = content;
    }

    public InvestnewFeedbackModel() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info == null ? null : contact_info.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", user_id=").append(user_id);
        sb.append(", title=").append(title);
        sb.append(", contact_info=").append(contact_info);
        sb.append(", contacts=").append(contacts);
        sb.append(", finish_time=").append(finish_time);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}