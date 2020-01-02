package com.jc.unity.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

/**
 * @author hx
 * @date 2019/8/17 10:14
 */
@Data
@NoArgsConstructor
public class FileMongoDO {

    private String id;
    private String name;

    private String contentType;

    private Long size;

    private Binary content;

    public FileMongoDO(String name, String contentType, Long size, Binary content) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ImageFileMongoDbDO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                '}';
    }
}
