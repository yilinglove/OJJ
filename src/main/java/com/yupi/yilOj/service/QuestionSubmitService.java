package com.yupi.yilOj.service;

import com.yupi.yilOj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yilOj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yilOj.model.entity.User;

/**
 *
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
