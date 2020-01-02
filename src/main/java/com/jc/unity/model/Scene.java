package com.jc.unity.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Blob;


/**
 * @author zjc
 * @date 2019/11/18
 */
@Data
@NoArgsConstructor
@TableName("scene")
@EqualsAndHashCode(callSuper = false)
public class Scene extends BaseEntity<Scene> {
    private String name;
    private byte[] content;
    private Long userId;
    private Long deployTime;
}
