package cn.yinjiahui.voa.portal.service;

import cn.yinjiahui.voa.db.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void upload(MultipartFile file,Integer teamId) throws IOException;

    List<FileInfo> getFile(Integer teamId);

    void deleteFile(Integer fileId);
}
