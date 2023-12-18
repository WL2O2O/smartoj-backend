package com.wl2o2o.smartoj.judge;

import com.wl2o2o.smartoj.model.entity.QuestionSubmit;
import com.wl2o2o.smartoj.model.vo.QuestionSubmitVO;

/**
 * 判题服务
 * @author WL2O2O
 * @create 2023/12/18 19:47
 */
public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
