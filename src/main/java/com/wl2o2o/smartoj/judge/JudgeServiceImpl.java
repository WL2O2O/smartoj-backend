package com.wl2o2o.smartoj.judge;

import cn.hutool.json.JSONUtil;
import com.wl2o2o.smartoj.common.ErrorCode;
import com.wl2o2o.smartoj.exception.BusinessException;
import com.wl2o2o.smartoj.judge.codesandbox.CodeSandBox;
import com.wl2o2o.smartoj.judge.codesandbox.CodeSandBoxFactory;
import com.wl2o2o.smartoj.judge.codesandbox.CodeSandBoxProxy;
import com.wl2o2o.smartoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.wl2o2o.smartoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.wl2o2o.smartoj.judge.strategy.JudgeContext;
import com.wl2o2o.smartoj.model.dto.question.JudgeCase;
import com.wl2o2o.smartoj.judge.codesandbox.model.JudgeInfo;
import com.wl2o2o.smartoj.model.entity.Question;
import com.wl2o2o.smartoj.model.entity.QuestionSubmit;
import com.wl2o2o.smartoj.model.enums.QuestionSubmitStatusEnum;
import com.wl2o2o.smartoj.service.QuestionService;
import com.wl2o2o.smartoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WL2O2O
 * @create 2023/12/18 19:55
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private Question question;

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeManager judgeManager;


    @Resource
    private QuestionSubmitService questionSubmitService;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在！");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在！");
        }
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中！");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 调用代码沙箱
        // 创建示例代码沙箱的测试类对象
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        CodeSandBoxProxy codeSandBoxProxy = new CodeSandBoxProxy(codeSandBox);

        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 正则表达式，判断样例中的输入输出 --> 获取输入，然后用 .collect 转换为数组
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBoxProxy.executeCode(executeCodeRequest);

        // 根据代码沙箱执行结果，判断运行结果是否则正确 --> 设置题目最终的判题状态与信息
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
