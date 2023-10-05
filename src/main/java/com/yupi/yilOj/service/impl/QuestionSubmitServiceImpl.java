package com.yupi.yilOj.service.impl;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yilOj.common.ErrorCode;
import com.yupi.yilOj.exception.BusinessException;
import com.yupi.yilOj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.yilOj.model.entity.Question;
import com.yupi.yilOj.model.entity.QuestionSubmit;
import com.yupi.yilOj.model.entity.User;
import com.yupi.yilOj.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.yilOj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.yilOj.service.QuestionService;
import com.yupi.yilOj.service.QuestionSubmitService;
import com.yupi.yilOj.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitMapper questionSubmitMapper;
    /**
     * 提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum enumByValue = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (enumByValue==null){
            throw  new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        //用户id
        questionSubmit.setUserId(userId);
        //用户提交的代码
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        //用户提交的问题id
        questionSubmit.setQuestionId(questionId);
        //用户所使用的代码语言
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        //todo 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据输入失败");
        }
        return questionSubmit.getId();
    }


}




