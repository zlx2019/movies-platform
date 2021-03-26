package com.movies.common.dto;

import com.movies.common.dto.group.CreateData;
import com.movies.common.validations.CustomValidationExample;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * Dto 数据校验 Demo
 * @author lx Zhang.
 * @date 2021/3/26 12:38 下午
 */
@Setter
@Getter
public class ExampleDto {

    /** 不能为Null*/
    @NotNull(message = "query不能为Null!")
    private String query;

    /** 不能为 ""*/
    @NotBlank(message = "不能为空!")
    private String name;

    /** 长度限制*/
    @Size(min = 6, max = 16,message = "密码长度6-16位字符！")
    private String passWord;

    /** 大小限制*/
    @Max(value = 100,message = "最大不能超过100")
    @Min(value = 10,message = "最小不能低于10")
    private Integer max;


    /** 正则格式校验*/
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message = "邮箱格式不正确！")
    private String email;

    /** 使用自定义校验注解*/
    @CustomValidationExample(message = "必须以北京为前缀",prefix = "北京")
    private String address;

    /**
     * groups = CreateData.class 表示只有指定了分组的Validated才会进行校验
     *  如:  @Validated(CreateData.class) ExampleDto dto
     * */
    @NotBlank(message = "name 不能为空字符串",groups = CreateData.class)
    private String names;
}
