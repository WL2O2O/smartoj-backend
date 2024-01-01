package com.wl2o2o.smartoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl2o2o.smartoj.annotation.AuthCheck;
import com.wl2o2o.smartoj.common.BaseResponse;
import com.wl2o2o.smartoj.common.ErrorCode;
import com.wl2o2o.smartoj.common.ResultUtils;
import com.wl2o2o.smartoj.constant.UserConstant;
import com.wl2o2o.smartoj.exception.BusinessException;
import com.wl2o2o.smartoj.model.dto.question.QuestionQueryRequest;
import com.wl2o2o.smartoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wl2o2o.smartoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wl2o2o.smartoj.model.entity.Question;
import com.wl2o2o.smartoj.model.entity.QuestionSubmit;
import com.wl2o2o.smartoj.model.entity.User;
import com.wl2o2o.smartoj.model.vo.QuestionSubmitVO;
import com.wl2o2o.smartoj.service.QuestionSubmitService;
import com.wl2o2o.smartoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/wl2o2o">程序员CSGUIDER</a>
 * @from <a href="https://wl2o2o.github.io">CSGUIDER博客</a>
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 题目提交 / 取消题目提交
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次题目提交变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能题目提交
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 分页获取题目列表（普通用户只能看到非代码的内容）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                   HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        // 返回脱敏信息
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

}
