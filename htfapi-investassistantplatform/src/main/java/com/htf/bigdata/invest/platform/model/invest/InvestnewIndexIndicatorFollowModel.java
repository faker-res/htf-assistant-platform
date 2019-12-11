package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;
import java.util.Date;

public class InvestnewIndexIndicatorFollowModel implements Serializable {
    private Long id;

    private Long object_id;

    private Integer type;

    private String user_id;

    private String creator;

    private String editor;

    private Date create_time;

    private Date update_time;

    private static final long serialVersionUID = 1L;

    public InvestnewIndexIndicatorFollowModel(Long id, Long object_id, Integer type, String user_id, String creator, String editor, Date create_time, Date update_time) {
        this.id = id;
        this.object_id = object_id;
        this.type = type;
        this.user_id = user_id;
        this.creator = creator;
        this.editor = editor;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public InvestnewIndexIndicatorFollowModel() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObject_id() {
        return object_id;
    }

    public void setObject_id(Long object_id) {
        this.object_id = object_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor == null ? null : editor.trim();
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
        sb.append(", object_id=").append(object_id);
        sb.append(", type=").append(type);
        sb.append(", user_id=").append(user_id);
        sb.append(", creator=").append(creator);
        sb.append(", editor=").append(editor);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}