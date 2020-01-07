package com.jc.unity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jc.unity.mapper.ModelTypeMapper;
import com.jc.unity.model.ModelTypeDO;
import com.jc.unity.service.ModelTypeService;
import org.springframework.stereotype.Service;

/**
 * @author zjc
 * @date 2020/1/2
 */
@Service
public class ModelTypeServiceImpl extends ServiceImpl<ModelTypeMapper, ModelTypeDO> implements ModelTypeService {
}
