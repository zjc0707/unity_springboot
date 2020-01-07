package com.jc.unity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zjc
 * @date 2020/1/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("model_type")
public class ModelTypeDO extends BaseEntity<ModelTypeDO> {
    private String name;
}
