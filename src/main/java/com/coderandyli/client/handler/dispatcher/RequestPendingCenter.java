package com.coderandyli.client.handler.dispatcher;

import com.coderandyli.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 请求等待中心
 * @Date 2021/6/10 5:18 下午
 * @Created by lizhenzhen
 */
public class RequestPendingCenter {
    public Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future) {
        this.map.put(streamId, future);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = this.map.get(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
            map.remove(streamId);
        }
    }
}
