package com.even.push.dto;

import java.io.Serializable;

/**
 * @Auther yidadi
 * @Date 17-9-1 下午4:24
 */
public class RegistDto extends BaseMsgDto implements Serializable{
    private static final long serialVersionUID = 7229390832544360153L;
    /**自己的标示*/
    private String mark;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
