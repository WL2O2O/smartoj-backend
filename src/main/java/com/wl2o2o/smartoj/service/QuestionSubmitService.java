package com.wl2o2o.smartoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wl2o2o.smartoj.model.dto.question.QuestionQueryRequest;
import com.wl2o2o.smartoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.wl2o2o.smartoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.wl2o2o.smartoj.model.entity.Question;
import com.wl2o2o.smartoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl2o2o.smartoj.model.entity.User;
import com.wl2o2o.smartoj.model.vo.QuestionSubmitVO;
import com.wl2o2o.smartoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对表 question_submit 的数据库操作 service
 *
 * @author WL2O2O
 * @create 2023/12/18 19:47
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionQueryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
