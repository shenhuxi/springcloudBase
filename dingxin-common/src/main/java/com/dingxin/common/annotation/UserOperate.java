package com.dingxin.common.annotation;

import java.lang.annotation.Documented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 记录用户操作日志
 * @author shixh
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
public @interface UserOperate {

    String name() default "";//具体操作,参照OperateConstant

    String business() default "";//操作业务

}
