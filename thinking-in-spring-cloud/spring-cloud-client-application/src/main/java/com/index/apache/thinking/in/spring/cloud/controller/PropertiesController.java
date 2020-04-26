package com.index.apache.thinking.in.spring.cloud.controller;

import com.index.apache.thinking.in.spring.cloud.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PropertiesController
 * @Description: properties 属性查询控制器
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/25 21:57
 * @Version： 1.0
 */
@RestController
@RequestMapping("/properties")
public class PropertiesController {

    @Autowired
    private PropertiesService propertiesService;

    @GetMapping("/author")
    public String getAuthor() {
        // 每一次调用刷新以后，被标注 @RefreshScope 注解的 bean 都将生成新的实例，参见其 javadoc
        System.out.println(propertiesService);
        return propertiesService.getAuthor();
    }
}
