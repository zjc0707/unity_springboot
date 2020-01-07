package com.jc.unity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jc.unity.mapper.ModelMapper;
import com.jc.unity.model.ModelDO;
import com.jc.unity.service.ModelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zjc
 * @date 2020/1/2
 */
@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, ModelDO> implements ModelService {
    @Override
    public boolean save(String name, String url, Long size) {
        ModelDO modelDO = new ModelDO();
        modelDO.setName(name);
        modelDO.setFileUrl(url);
        modelDO.setSize(size);
        modelDO.setDeployTime(System.currentTimeMillis()/1000);
        return this.save(modelDO);
    }

    @Override
    public List<ModelDO> listByTypeId(Long typeId) {
        return super.list(new QueryWrapper<ModelDO>().lambda().eq(ModelDO::getModelTypeId, typeId));
    }
}
