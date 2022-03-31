package com.example.springdemo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springdemo.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    private static final String host = "http://localhost";

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        // 定义文件前缀 唯一标识符
        String flag = IdUtil.fastSimpleUUID();
        String rootFilePath = System.getProperty("user.dir") + "/spring-demo/src/main/resources/files/"
                + flag + "_" + originalFileName;
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        return Result.success(host + ":" + port + "/files/" + flag);  // 返回 url
    }

    @GetMapping("{flag}")
    public void getFiles( @PathVariable String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流
        String basePath = System.getProperty("user.dir") + "/spring-demo/src/main/resources/files/";  // 定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String targetFileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到与参数一致的文件
        try {
            if (StrUtil.isNotEmpty(targetFileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(targetFileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + targetFileName);  // 读取文件字节流
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
