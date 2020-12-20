package com.nowcoder.community.entity;

import java.util.Date;

public class Message {
    private int id ;
    private int fromId;//发送人用户id from_id=1为系统用户，及系统给你发的通知
    private int toId;//接收者用户id
    private String conversationId;//会话id，发送者与接受者的用户id拼起来，小的用户id放在前面，两个id之间使用_连接起来
    private String content;
    private int status;//0表示未读，1表示已读，2表示删除
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", conversationId='" + conversationId + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
