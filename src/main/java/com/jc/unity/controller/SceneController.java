package com.jc.unity.controller;

import com.jc.unity.model.ResultData;
import com.jc.unity.model.SceneDO;
import com.jc.unity.service.SceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zjc
 * @date 2019/11/18
 */
@Slf4j
@RestController
@RequestMapping("/scene")
public class SceneController {
    @Autowired
    public SceneService sceneService;

    @PostMapping("/page")
    public ResultData page(Integer startIndex, Integer pageSize){
        try{
            return ResultData.SUCCESS(sceneService.page(startIndex, pageSize));
        }catch (Exception e){
            return ResultData.FAILURE(e.toString());
        }
    }

    @PostMapping("/detail")
    public ResultData detail(Long id){
        try{
            return ResultData.SUCCESS(sceneService.getById(id));
        }catch (Exception e){
            return ResultData.FAILURE(e.toString());
        }
    }

    @PostMapping("/save")
    public ResultData save(String name, MultipartFile content) throws IOException {
        SceneDO scene = new SceneDO();
        scene.setName(name);
        scene.setContent(content.getBytes());
        scene.setDeployTime(System.currentTimeMillis()/1000);
        try{
            return sceneService.save(scene) ? ResultData.SUCCESS("提交成功"):ResultData.FAILURE("提交失败");
        }catch (Exception e){
            return ResultData.FAILURE(e.toString());
        }
    }
}
