package com.even.push.dto;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:48
 */
public class BaseMsgDto {
    /**注册订阅的topic的前缀*/
    private String topicPre;

    public String getTopicPre() {
        return topicPre;
    }

    public void setTopicPre(String topicPre) {
        this.topicPre = topicPre;
    }
}
