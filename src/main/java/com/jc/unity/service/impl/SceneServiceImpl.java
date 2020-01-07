package com.jc.unity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jc.unity.mapper.SceneMapper;
import com.jc.unity.model.SceneDO;
import com.jc.unity.service.SceneService;
import org.springframework.stereotype.Service;

/**
 * @author zjc
 * @date 2019/11/18
 */
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, SceneDO> implements SceneService {

    @Override
    public IPage<SceneDO> page(Integer startIndex, Integer pageSize) {
        Page<SceneDO> page = new Page<>(startIndex, pageSize);
        page.addOrder(OrderItem.desc("deploy_time"));

        return super.page(page, Wrappers.query(new SceneDO()).select("id","name","user_id","deploy_time"));
    }
}
