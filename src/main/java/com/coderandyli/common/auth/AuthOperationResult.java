package com.coderandyli.common.auth;

import com.coderandyli.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
