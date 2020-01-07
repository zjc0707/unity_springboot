package com.jc.unity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jc.unity.model.SceneDO;

/**
 * @author zjc
 * @date 2019/11/18
 */
public interface SceneService extends IService<SceneDO> {
    IPage<SceneDO> page(Integer startIndex, Integer pageSize);
}
