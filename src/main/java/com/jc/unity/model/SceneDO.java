package com.jc.unity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author zjc
 * @date 2019/11/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("scene")
public class SceneDO extends BaseEntity<SceneDO> {
    private String name;
    private byte[] content;
    private Long userId;
    private Long deployTime;
}
