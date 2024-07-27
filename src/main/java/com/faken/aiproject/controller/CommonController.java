package com.faken.aiproject.controller;

import com.faken.aiproject.po.result.Result;
import com.faken.aiproject.util.HuaweiOBSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
public class CommonController {

    @Autowired
    private HuaweiOBSUtils huaweiOBSUtils;

    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = null;
        if (originalFilename != null) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        String uniqueFilename = UUID.randomUUID() + "." + fileExtension;

        // 转化文件格式为File类型
        File file = convertMultipartFileToFile(multipartFile);

        huaweiOBSUtils.uploadToOBS(file, uniqueFilename);
        String url = huaweiOBSUtils.generateOBSUrl(uniqueFilename);

        file.delete();
        return Result.success("", url);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("temp", multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

}
