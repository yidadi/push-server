package com.even.push.dto;

/**
 * @Auther yidadi
 * @Date 17-9-4 下午3:40
 */
public class SubMsgDto extends BaseMsgDto{
    private String Mqtopic;
    private String content;
    private String mark;
    /**消息的投递次数  0 1 2*/
    private int qos;

    public String getMqtopic() {
        return Mqtopic;
    }

    public void setMqtopic(String mqtopic) {
        Mqtopic = mqtopic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }
}
