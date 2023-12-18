package com.wl2o2o.smartoj.judge.codesandbox.impl;

import com.wl2o2o.smartoj.judge.codesandbox.CodeSandBox;
import com.wl2o2o.smartoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wl2o2o.smartoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱接口（实际调用我们开发的接口代码沙箱）
 * @author WL2O2O
 * @create 2023/12/16 16:58
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
