package com.example.springboot02.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by zhang on 2018/9/4.<br/>
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Component
//@ConfigurationProperties(prefix = "user") // 从配置文件注入属性
public class User {
    private Integer userId;

    /*
     * Bean校验：在bean中使用注解约束，在Controller接收的参数中使用@Validated注解，并使用BindingResult接受异常参数
     */
    private String username;

//    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    private Integer age;

    @JSONField(name = "ct")
    private Date createTime;
}
