package com.dingxin.common.annotation;


import java.lang.annotation.*;

import com.dingxin.common.constant.EncryptionConstant;

/**
 * 字段加密
 * @author shixh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Encryption {
 
    String name() default EncryptionConstant.ENCRYPTION_A;

 }
