package com.jc.unity.controller;

import com.jc.unity.model.ModelTypeDO;
import com.jc.unity.model.ResultData;
import com.jc.unity.service.ModelTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zjc
 * @date 2020/1/9
 */
@Slf4j
@RestController
@RequestMapping("/modelType")
public class ModelTypeController {
    @Autowired
    ModelTypeService modelTypeService;

    @GetMapping("/findList")
    public ResultData findList(){
        try {
            return ResultData.SUCCESS(modelTypeService.list());
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }

    @GetMapping("/add")
    public ResultData Add(String name){
        ModelTypeDO modelTypeDO = new ModelTypeDO();
        modelTypeDO.setName(name);
        try{
            modelTypeService.save(modelTypeDO);
            return ResultData.SUCCESS(modelTypeDO);
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }
}
