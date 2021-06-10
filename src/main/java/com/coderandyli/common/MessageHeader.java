package com.coderandyli.common;

import lombok.Data;

/**
 * @Classname MessageHeader
 * @Date 2021/6/9 3:42 下午
 * @Created by lizhenzhen
 */
@Data
public class MessageHeader {
    /**
     * 版本号
     */
    private int version = 1;
    /**
     * 操作code
     */
    private int opCode;
    /**
     *
     */
    private long streamId;
    
}
