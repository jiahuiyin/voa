package cn.yinjiahui.voa.db.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class UserInfo {
    private Integer id;

    private String phone;

    private String username;

    private String password;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private Integer gender;

    private String avatar;

    private Integer online;

}
