package com.wl2o2o.smartoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wl2o2o.smartoj.model.dto.question.QuestionQueryRequest;
import com.wl2o2o.smartoj.model.entity.Question;
import com.wl2o2o.smartoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 针对表 question 的数据库操作 service
 *
 * @author WL2O2O
 * @create 2023/12/18 19:47
 */
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    

    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionSubmitPage, HttpServletRequest request);
}
