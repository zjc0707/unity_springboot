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
@TableName("model")
public class ModelDO extends BaseEntity<ModelDO> {
    private String name;
    private String fileUrl;
    private Long modelTypeId;
    private Long size;
    private Long deployTime;
}
