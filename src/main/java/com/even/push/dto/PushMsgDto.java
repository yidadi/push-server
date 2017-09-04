package com.even.push.dto;

import java.io.Serializable;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:32
 */
public class PushMsgDto extends BaseMsgDto implements Serializable{
    private static final long serialVersionUID = -1432275044516236784L;
    /**消息体内容  格式自己约定*/
    private String content;
    /**消息的投递次数  0 1 2*/
    private int qos;
    /**自己的标示*/
    private String mark;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
