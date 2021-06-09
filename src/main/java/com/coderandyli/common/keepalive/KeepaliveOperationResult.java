package com.coderandyli.common.keepalive;

import com.coderandyli.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
