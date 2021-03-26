package com.movies.common.validations;

import com.movies.common.validations.impl.CustomValidationExampleImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义数据校验 Demo
 *  以String类型为例,该字符必须以 'xx' 为前缀
 * @author lx Zhang.
 * @date 2021/3/26 1:07 下午
 * @apiNote 实现类: CustomValidationExampleImpl
 */
@Constraint(validatedBy = {CustomValidationExampleImpl.class})
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValidationExample {

    /** 校验失败消息*/
    String message() default "数据校验失败!";

    /** 校验分组*/
    Class<?>[] groups() default {};

    /** 前缀条件*/
    String prefix();

    Class<? extends Payload>[] payload() default {};
}
