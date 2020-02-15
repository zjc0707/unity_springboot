package com.jc.unity.controller;

import com.jc.unity.model.ModelTypeDO;
import com.jc.unity.model.ResultData;
import com.jc.unity.service.ModelTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("/findList")
    public ResultData findList(){
        try {
            List<ModelTypeDO> list = modelTypeService.list();
            ModelTypeDO modelTypeDO = new ModelTypeDO();
            modelTypeDO.setId(0L);
            modelTypeDO.setName("通用");
            list.add(modelTypeDO);
            return ResultData.SUCCESS(list);
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }
}
