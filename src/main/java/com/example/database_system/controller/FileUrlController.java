package com.example.database_system.controller;

import com.example.database_system.pojo.dto.OptionResourceDto;
import com.example.database_system.pojo.response.ResponseMessage;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@Tag(name = "文件上传", description = "文件上传相关接口")
public class FileUrlController {
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    @Operation(summary = "上传文件", description = "用于上传文件并返回文件的访问 URL")
    public ResponseMessage<OptionResourceDto> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            file.transferTo(filePath.toFile());
            return ResponseMessage.success(
                    new OptionResourceDto(null, "http://localhost:8080/files/" + fileName),
                    "文件上传成功"
            );
        } catch (IOException e) {
            return ResponseMessage.error(null, "文件上传失败: " + e.getMessage(),HttpStatus.BAD_REQUEST.value());
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
