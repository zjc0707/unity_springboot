package com.jc.unity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zjc
 * @date 2020/1/2
 */
@Data
@AllArgsConstructor
public class ResultData<T> {
    public static <T> ResultData SUCCESS(T obj){return new ResultData<>(true, obj);}
    public static <T> ResultData FAILURE(T obj){return new ResultData<>(false, obj);}

    private boolean success;
    private T obj;
}
