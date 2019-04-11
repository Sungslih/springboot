package com.xwbing.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 说明: 德鲁伊数据源配置。监控地址:druid/index.html
 * 项目名称: boot-module-demo
 * 创建时间: 2017/12/10 16:36
 * 作者:  xiangwb
 */
@Configuration
@EnableConfigurationProperties(DruidDataSourceModel.class)
public class DruidDataSourceConfig {
    @Resource
    private DruidDataSourceModel druidDataSourceModel;
    private final Logger logger = LoggerFactory.getLogger(DruidDataSourceConfig.class);

    @Bean
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidDataSourceModel.getUrl());
        dataSource.setDriverClassName(druidDataSourceModel.getDriver());
        dataSource.setUsername(druidDataSourceModel.getUsername());
        dataSource.setPassword(druidDataSourceModel.getPassword());
        dataSource.setInitialSize(druidDataSourceModel.getInitSize());
        dataSource.setMinIdle(druidDataSourceModel.getMinIdle());
        dataSource.setMaxActive(druidDataSourceModel.getMaxActive());
        dataSource.setMaxWait(druidDataSourceModel.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceModel.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidDataSourceModel.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(druidDataSourceModel.getValidationQuery());
        dataSource.setTestWhileIdle(druidDataSourceModel.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidDataSourceModel.isTestOnBorrow());
        dataSource.setTestOnReturn(druidDataSourceModel.isTestOnReturn());
        dataSource.setPoolPreparedStatements(druidDataSourceModel.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDataSourceModel.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setConnectionProperties(druidDataSourceModel.getConnectionProperties());
        dataSource.setUseGlobalDataSourceStat(druidDataSourceModel.isUseGlobalDataSourceStat());
        try {
            dataSource.setFilters(druidDataSourceModel.getFilters());
        } catch (SQLException e) {
            logger.error("配置druid过滤器异常");
        }
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean statViewServlet() {
        logger.info("注册druid监控信息显示statViewServlet ======================= ");
        ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        //添加初始化参数:initParams
//        registration.addInitParameter("allow", "127.0.0.1");//IP白名单(没有配置或者为空,则允许所有访问)
//        registration.addInitParameter("deny", "172.17.21.232");//IP黑名单(存在共同时,deny优先于allow)
        registration.addInitParameter("loginUsername", "admin");//用户名
        registration.addInitParameter("loginPassword", "123456");//密码
        registration.addInitParameter("resetEnable", "true");//禁用HTML页面上的"Reset All"功能
        return registration;
    }

    @Bean
    public FilterRegistrationBean webStatFilter() {
        logger.info("注册druid监控信息采集webStatFilter ======================= ");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,*.jsp,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");//监控单个url调用的sql列表
        return filterRegistrationBean;
    }
}
