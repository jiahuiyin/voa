package cn.yinjiahui.voa.db.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageInfo {
    private Integer id;
    private String action;
    private String content;
    private String sender;
    private String receiver;
    private Date creatTime;
}
