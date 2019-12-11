package com.htf.bigdata.invest.platform.model.invest;

import java.io.Serializable;
import java.util.Date;

public class InvestnewMessageModel implements Serializable {
    private Long id;

    private String sender_user_id;

    private String receiver_user_id;

    private Long broadcast_id;

    private Boolean is_read;

    private Boolean is_revoke;

    private Date read_time;

    private String object_id;

    private String object_type;

    private String object_pid;

    private Date create_time;

    private Date update_time;

    private String message;

    private static final long serialVersionUID = 1L;

    public InvestnewMessageModel(Long id, String sender_user_id, String receiver_user_id, Long broadcast_id, Boolean is_read, Boolean is_revoke, Date read_time, String object_id, String object_type, String object_pid, Date create_time, Date update_time) {
        this.id = id;
        this.sender_user_id = sender_user_id;
        this.receiver_user_id = receiver_user_id;
        this.broadcast_id = broadcast_id;
        this.is_read = is_read;
        this.is_revoke = is_revoke;
        this.read_time = read_time;
        this.object_id = object_id;
        this.object_type = object_type;
        this.object_pid = object_pid;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public InvestnewMessageModel(Long id, String sender_user_id, String receiver_user_id, Long broadcast_id, Boolean is_read, Boolean is_revoke, Date read_time, String object_id, String object_type, String object_pid, Date create_time, Date update_time, String message) {
        this.id = id;
        this.sender_user_id = sender_user_id;
        this.receiver_user_id = receiver_user_id;
        this.broadcast_id = broadcast_id;
        this.is_read = is_read;
        this.is_revoke = is_revoke;
        this.read_time = read_time;
        this.object_id = object_id;
        this.object_type = object_type;
        this.object_pid = object_pid;
        this.create_time = create_time;
        this.update_time = update_time;
        this.message = message;
    }

    public InvestnewMessageModel() {
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

    public String getReceiver_user_id() {
        return receiver_user_id;
    }

    public void setReceiver_user_id(String receiver_user_id) {
        this.receiver_user_id = receiver_user_id == null ? null : receiver_user_id.trim();
    }

    public Long getBroadcast_id() {
        return broadcast_id;
    }

    public void setBroadcast_id(Long broadcast_id) {
        this.broadcast_id = broadcast_id;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    public Boolean getIs_revoke() {
        return is_revoke;
    }

    public void setIs_revoke(Boolean is_revoke) {
        this.is_revoke = is_revoke;
    }

    public Date getRead_time() {
        return read_time;
    }

    public void setRead_time(Date read_time) {
        this.read_time = read_time;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id == null ? null : object_id.trim();
    }

    public String getObject_type() {
        return object_type;
    }

    public void setObject_type(String object_type) {
        this.object_type = object_type == null ? null : object_type.trim();
    }

    public String getObject_pid() {
        return object_pid;
    }

    public void setObject_pid(String object_pid) {
        this.object_pid = object_pid == null ? null : object_pid.trim();
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
        sb.append(", receiver_user_id=").append(receiver_user_id);
        sb.append(", broadcast_id=").append(broadcast_id);
        sb.append(", is_read=").append(is_read);
        sb.append(", is_revoke=").append(is_revoke);
        sb.append(", read_time=").append(read_time);
        sb.append(", object_id=").append(object_id);
        sb.append(", object_type=").append(object_type);
        sb.append(", object_pid=").append(object_pid);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_time=").append(update_time);
        sb.append(", message=").append(message);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}