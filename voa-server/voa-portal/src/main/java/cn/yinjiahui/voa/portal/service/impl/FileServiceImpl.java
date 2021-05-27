package cn.yinjiahui.voa.portal.service.impl;

import cn.yinjiahui.voa.db.mapper.FileMapper;
import cn.yinjiahui.voa.db.model.FileInfo;
import cn.yinjiahui.voa.portal.service.FileService;
import cn.yinjiahui.voa.portal.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${voa.filepath}")
    private String filePath;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public void upload(MultipartFile file,Integer teamId) throws IOException{
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        String trueName=UUID.randomUUID()+suffixName;
        File dest = new File(filePath+ trueName);
        file.transferTo(dest);

        FileInfo fileInfo=new FileInfo();
        fileInfo.setFileName(fileName);
        fileInfo.setTeamId(teamId);
        fileInfo.setTrueName(trueName);
        fileInfo.setFileSize(FileUtil.getSize(file.getSize()));

        fileMapper.addFile(fileInfo);

    }

    @Override
    public List<FileInfo> getFile(Integer teamId) {
        List<FileInfo> file = fileMapper.getFile(teamId);
        return file;
    }

    @Override
    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
