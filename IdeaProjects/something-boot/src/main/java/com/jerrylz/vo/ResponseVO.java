package com.jerrylz.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * created by jerrylz
 * date 2020/3/26
 */
@Data
public class ResponseVO implements Serializable {

    private String code;
    private String message;
}
