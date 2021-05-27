package cn.yinjiahui.voa.db.mapper;

import cn.yinjiahui.voa.db.model.FileInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FileMapper {

    @Insert("INSERT INTO `voa_file` (filename,filesize,teamid,truename)" +
            " VALUES(#{fileName},#{fileSize},#{teamId},#{trueName})")
    void addFile(FileInfo fileInfo);

    @Select("SELECT id,filename,filesize,truename FROM voa_file WHERE teamid=#{teamId}")
    @Results({
            @Result(property = "fileName",column = "filename"),
            @Result(property = "fileSize",column = "filesize"),
            @Result(property = "trueName",column = "truename")
    })
    List<FileInfo> getFile(Integer teamId);

    @Delete("DELETE FROM voa_file WHERE id=#{fileId}")
    void deleteFile(Integer fileId);

}