package com.xwbing.configuration;

import com.xwbing.util.captcha.CaptchaServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 说明: 统一servlet/filter配置
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Slf4j
@Configuration
public class ServletFilterConfig {
    @Bean
    public ServletRegistrationBean captchaServlet() {
        log.info("注册登陆验证码servlet ======================= ");
        ServletRegistrationBean registration = new ServletRegistrationBean(new CaptchaServlet());
        registration.addUrlMappings("/captcha");
        return registration;
    }

    /*servlet*****************************************************filter*/

//    @Bean
//    public FilterRegistrationBean formSaveFilter() {
//        log.info("注册表单重复提交过滤器formSaveFilter ======================= ");
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new FormRepeatFilter());
//        filterRegistrationBean.addUrlPatterns("/*");
//        filterRegistrationBean.addInitParameter("excludePath", "/doc,/captcha,/v2/api-docs,/swagger-resources,/configuration/ui,/configuration/security,/druid");
//        filterRegistrationBean.addInitParameter("excludeType", ".js,.css,.gif,.jpg,.png,.ico,.jsp,.html,/druid/");
//        return filterRegistrationBean;
//    }
}
