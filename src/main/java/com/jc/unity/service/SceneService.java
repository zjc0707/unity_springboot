package com.jc.unity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jc.unity.model.Scene;

/**
 * @author zjc
 * @date 2019/11/18
 */
public interface SceneService extends IService<Scene> {
    IPage<Scene> page(Integer startIndex, Integer pageSize);
}
