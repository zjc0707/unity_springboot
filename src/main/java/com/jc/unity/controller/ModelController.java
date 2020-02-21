package com.jc.unity.controller;

import com.jc.unity.model.ModelDO;
import com.jc.unity.model.ResultData;
import com.jc.unity.service.FileMongoService;
import com.jc.unity.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
     */
    @GetMapping("/findList")
    public ResultData findList(){
        try{
            List<ModelDO> rs = this.modelService.list();
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
     * 根据模型id查找
     * @param id 模型id
     */
    @GetMapping("/findById")
    public ResultData findById(Long id){
        try{
            return ResultData.SUCCESS(modelService.getById(id));
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }

    /**
     * 上传模型文件
     * @param name 模型名称
     * @param fileWindows 模型文件
     * @param fileMac 模型文件
     * @return 先上传至mongodb，成功后再写入mysql数据库，后者失败则删除mongodb的数据
     */
    @PostMapping("/upload")
    public ResultData upload(String name, Long typeId, MultipartFile fileWindows, MultipartFile fileMac){
        try{
            String urlWindows = fileMongoService.saveFile(fileWindows);
            String urlMac = fileMongoService.saveFile(fileMac);
            if(!urlWindows.isEmpty() && !urlMac.isEmpty()){
                if(modelService.save(name, typeId, urlMac, urlWindows, fileMac.getSize()/1024)){
                    log.info("success:[name:" + name + ", urlMac:" + urlMac + ", urlWindows:" + urlWindows + "]");
                    return ResultData.SUCCESS("upload success");
                }else{
                    log.error("fail:[name: " + name + "]");
                    fileMongoService.removeFile(urlMac);
                    fileMongoService.removeFile(urlWindows);
                    return ResultData.FAILURE("fail:[name: " + name + "]");
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
            fileMongoService.removeFile(modelDO.getFileUrlMac());
            fileMongoService.removeFile(modelDO.getFileUrlWindows());
            modelService.removeById(id);
            log.info("remove success");
            return ResultData.SUCCESS("");
        }catch (Exception e){
            log.error(e.toString());
            return ResultData.FAILURE(e.toString());
        }
    }
}
