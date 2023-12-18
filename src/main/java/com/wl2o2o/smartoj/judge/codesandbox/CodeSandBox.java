package com.wl2o2o.smartoj.judge.codesandbox;

import com.wl2o2o.smartoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wl2o2o.smartoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 *
 * @author WL2O2O
 * @create 2023/12/16 16:20
 */
public interface CodeSandBox {
    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
