package com.wl2o2o.smartoj.judge;

import com.wl2o2o.smartoj.judge.strategy.DefaultJudgeStrategy;
import com.wl2o2o.smartoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.wl2o2o.smartoj.judge.strategy.JudgeContext;
import com.wl2o2o.smartoj.judge.strategy.JudgeStrategy;
import com.wl2o2o.smartoj.model.dto.questionsubmit.JudgeInfo;
import com.wl2o2o.smartoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 * @author WL2O2O
 * @create 2023/12/19 1:20
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
