package com.jc.unity.controller;

import com.jc.unity.model.ModelDO;
import com.jc.unity.model.ResultData;
import com.jc.unity.service.FileMongoService;
import com.jc.unity.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author zjc
 * @date 2020/1/2
 */
@Slf4j
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private FileMongoService fileMongoService;

    @Autowired
    private ModelService modelService;

    /**
     * 查看选择类型的全部模型基本数据
     * @param typeId 模型类型id，默认0为通用模型
     */
    @GetMapping("/findList")
    public ResultData findList(@RequestParam(defaultValue = "0") Long typeId){
        try{
            List<ModelDO> rs = this.modelService.listByTypeId(typeId);
            if(rs == null || rs.isEmpty()){
                log.error("not found");
                return ResultData.FAILURE("not found");
            }else{
                return ResultData.SUCCESS(rs);
            }
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE("exception");
        }
    }

    /**
     * 上传模型文件
     * @param name 模型名称
     * @param file 模型文件
     * @return 先上传至mongodb，成功后再写入mysql数据库，后者失败则删除mongodb的数据
     */
    @PostMapping("/upload")
    public ResultData upload(String name, MultipartFile file){
        try{
            String url = fileMongoService.saveFile(file);
            if(!url.isEmpty()){
                if(modelService.save(name, url, file.getSize()/1024)){
                    log.info("success:[name:" + name + ", url:" + url + "]");
                    return ResultData.SUCCESS("");
                }else{
                    log.error("fail:[name: " + name + "]");
                    fileMongoService.removeFile(url);
                    return ResultData.FAILURE("");
                }
            }
            log.error("url empty");
            return ResultData.FAILURE("url empty");
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }

    /**
     * 根据id删除模型数据
     * @param id 模型id
     * @return 删除mongodb数据，逻辑删除mysql数据
     */
    @PostMapping("/remove")
    public ResultData remove(Long id){
        try{
            ModelDO modelDO = modelService.getById(id);
            if(null == modelDO){
                log.error("not found id=" + id);
                return ResultData.FAILURE("result = null: id=" + id);
            }
            fileMongoService.removeFile(modelDO.getFileUrl());
            modelService.removeById(id);
            log.info("remove success");
            return ResultData.SUCCESS("");
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }
}
