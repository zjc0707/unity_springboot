package com.jc.unity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jc.unity.model.ModelDO;

import java.util.List;

/**
 * @author zjc
 * @date 2020/1/2
 */
public interface ModelService extends IService<ModelDO> {
    boolean save(String name, Long typeId, String fileUrlMac, String fileUrlWindows, Long size);
    List<ModelDO> listByTypeId(Long typeId);
}
