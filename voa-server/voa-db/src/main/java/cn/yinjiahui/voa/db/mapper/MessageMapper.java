package cn.yinjiahui.voa.db.mapper;

import cn.yinjiahui.voa.db.model.MessageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper {

    @Options(useGeneratedKeys = true, keyProperty = "message.id", keyColumn = "id")
    @Insert("INSERT INTO `voa_message` (message_action,content,sender,receiver,is_visited) " +
            "VALUES (#{message.action},#{message.content},#{message.sender},#{message.receiver},#{visited})")
    void addMessage(@Param("message")MessageInfo message, @Param("visited") Integer visited);



    @Select("SELECT message_action,content,sender,receiver  FROM voa_message WHERE (sender=#{senderId} AND receiver=#{receiverId})OR (sender=#{receiverId} AND receiver=#{senderId})")
    @Result(column = "aessage_action",property = "action")
    List<MessageInfo> getMessage(Integer senderId, Integer receiverId);



}
