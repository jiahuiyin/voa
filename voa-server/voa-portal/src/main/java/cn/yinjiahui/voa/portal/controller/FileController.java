package cn.yinjiahui.voa.portal.controller;


import cn.yinjiahui.voa.common.api.CommonResult;
import cn.yinjiahui.voa.db.model.FileInfo;
import cn.yinjiahui.voa.portal.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("")
    public CommonResult upload(@RequestParam("file") MultipartFile file,@RequestParam Integer teamId)  {
        if (file.isEmpty()) {
            return CommonResult.failed();
        }
        try {
            fileService.upload(file,teamId);
        } catch (IOException e) {
            log.error(e.getMessage());
            return CommonResult.failed();
        }
        return getFile(teamId);

    }

    @GetMapping("")
    public CommonResult getFile(Integer teamId){
        List<FileInfo> file = fileService.getFile(teamId);
        return CommonResult.success(file);
    }
    @DeleteMapping("")
    public CommonResult deleteFile(Integer fileId){
        fileService.deleteFile(fileId);
        return CommonResult.success(null);
    }
}
