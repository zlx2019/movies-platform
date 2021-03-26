package com.movies.common.validations.impl;

import com.movies.common.validations.CustomValidationExample;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义校验实现 实现ConstraintValidator接口<annotation,dataType>
 * @author lx Zhang.
 * @date 2021/3/26 1:13 下午
 */
public class CustomValidationExampleImpl implements ConstraintValidator<CustomValidationExample,String> {

    //前缀条件
    private String prefix;

    /**
     * 获取注解上下文,将前缀条件取出
     * @Author lx Zhang.
     * @Date 2021/3/26 1:22 下午
     * @Param [constraintAnnotation]
     **/
    @Override
    public void initialize(CustomValidationExample constraintAnnotation) {
        prefix = constraintAnnotation.prefix();
    }

    /**
     * 校验逻辑实现 true为通过
     * @Author lx Zhang.
     * @Date 2021/3/26 1:20 下午
     * @Param [data, constraintValidatorContext]
     * @return boolean
     **/
    @Override
    public boolean isValid(String data, ConstraintValidatorContext constraintValidatorContext) {
        if (prefix == null || data == null){
            return true;
        }
        return data.startsWith(prefix);
    }
}
