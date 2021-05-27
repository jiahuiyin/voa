package cn.yinjiahui.voa.db.model;

import lombok.Data;

@Data
public class FileInfo {

    Integer id;

    String fileName;

    String fileSize;

    Integer teamId;

    String trueName;
}
