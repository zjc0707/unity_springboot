package com.jc.unity.controller;

import com.jc.unity.model.FileMongoDO;
import com.jc.unity.model.ResultData;
import com.jc.unity.service.FileMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author zjc
 * @date 2020/1/2
 */
@Slf4j
@RestController
@RequestMapping("/fileServer")
public class FileMongoController {

    @Autowired
    FileMongoService fileMongoService;

    /**
     * 从mongodb下载模型文件数据
     * @param fileSlot mongodb bucket或collection标号
     * @param id 模型文件数据id
     * @return 使用ResponseEntity使下载能得到进度
     */
    @GetMapping("/download/{fileSlot}/{id}")
    public ResponseEntity<Object> download(@PathVariable String fileSlot, @PathVariable String id) throws UnsupportedEncodingException {
        FileMongoDO resultEntity = fileMongoService.getFileById(fileSlot, id);
        if ((null != resultEntity) && (resultEntity.getSize() > 0)) {
            log.info("result:" + resultEntity);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=".concat(new String(resultEntity.getName().getBytes("utf-8"), "ISO-8859-1")))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, resultEntity.getSize().toString())
                    .header("Connection", "close")
                    .body(resultEntity.getContent().getData());
        }else{
            log.error("not found: " + fileSlot + '/' + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found");
        }
    }
}
