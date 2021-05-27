package cn.yinjiahui.voa.db.mapper;

import cn.yinjiahui.voa.db.model.UserInfo;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Select("SELECT phone,user_password FROM voa_user WHERE phone=#{phone}")
    @Result(column = "user_password",property = "password")
    UserInfo getUserAuthByPhone(String phone);

    @Select("SELECT id FROM voa_user WHERE phone=#{phone}")
    String getUserIdByPhone(String phone);



//    @Insert("INSERT  INTO `voa_team_user`(`team_id`,`user_id`) VALUES (#{a},#{id})")
//    void insert(Integer a ,Integer id);

    @Select("SELECT username FROM voa_user WHERE phone=#{phone}")
    String getUsernameByPhone(String phone);


    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO voa_user (phone,username,user_password,gender)" +
            "VALUE(#{phone},#{username},#{password},#{gender})")
    void addUser(UserInfo userInfo);


    @Select("SELECT username FROM voa_user WHERE id=#{id}")
    String getUsernameById(Integer id);

    @Update("UPDATE voa_user SET avatar=#{avatar} WHERE id=#{id}")
    void updateAvatar(String avatar,Integer id);

    @Select("SELECT id,phone,username,avatar FROM voa_user WHERE phone=#{phone}")
    UserInfo getUserInfoByPhone(String phone);

    @Select("SELECT username,gender,birthday,avatar FROM voa_user WHERE id=#{id}")
    UserInfo getUserInfo(Integer id);

    @Update("UPDATE voa_user SET username=#{username},gender=#{gender},birthday=#{birthday} WHERE id=#{id}")
    void updateUserInfo(UserInfo userInfo);

}
