package com.wl2o2o.smartoj.judge.codesandbox.model;

import com.wl2o2o.smartoj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WL2O2O
 * @create 2023/12/16 16:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputList;

    /**
     * 接口信息
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    private JudgeInfo judgeInfo;
}
