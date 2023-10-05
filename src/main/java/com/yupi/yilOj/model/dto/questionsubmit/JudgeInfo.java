package com.yupi.yilOj.model.dto.questionsubmit;

import lombok.Data;

/**
 * 题目判题信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private Long message;
    /**
     * 消耗内存
     */
    private Long memory;
    /**
     * 堆栈限制
     */
    private Long time;
}
