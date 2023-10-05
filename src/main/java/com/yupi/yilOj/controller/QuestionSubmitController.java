package com.yupi.yilOj.controller;

import com.yupi.yilOj.common.BaseResponse;
import com.yupi.yilOj.common.ErrorCode;
import com.yupi.yilOj.common.ResultUtils;
import com.yupi.yilOj.exception.BusinessException;

import com.yupi.yilOj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yilOj.model.entity.User;
import com.yupi.yilOj.service.QuestionSubmitService;

import com.yupi.yilOj.service.UserService;
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
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/question_submit")
//@Slf4j
public class QuestionSubmitController {

    @Resource
    QuestionSubmitService questionSubmitService;
    @Resource
    UserService userService;
    /**
     * 提交題目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                                  HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long questionId = questionSubmitAddRequest.getQuestionId();
        Long result = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }
}
