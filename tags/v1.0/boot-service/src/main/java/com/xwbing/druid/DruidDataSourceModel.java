package com.xwbing.druid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 说明: 德鲁伊数据源参数模型
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Data
@Component
@PropertySource("classpath:druid.properties")
@ConfigurationProperties(prefix = "jdbc")
public class DruidDataSourceModel {
    private String url;
    private String username;
    private String password;
    private String driver;
    /**
     * 初始连接数
     */
    private int initSize;
    /**
     * 最小空闲
     */
    private int minIdle;
    /**
     * 最大连接数
     */
    private int maxActive;
    /**
     * 连接等待超时时间
     */
    private int maxWait;
    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private int timeBetweenEvictionRunsMillis;
    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    private int minEvictableIdleTimeMillis;
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句
     */
    private String validationQuery;
    /**
     * 申请连接的时候检测
     */
    private boolean testWhileIdle;
    /**
     * 申请连接时执行validationQuery检测连接是否有效，配置为true会降低性能
     */
    private boolean testOnBorrow;
    /**
     * 归还连接时执行validationQuery检测连接是否有效，配置为true会降低性能
     */
    private boolean testOnReturn;
    /**
     * 打开PSCache,mysql5.5以下的版本中没有PSCache功能，建议关闭掉
     */
    private boolean poolPreparedStatements;
    /**
     * 指定每个连接上PSCache的大小
     */
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     * 配置监控统计拦截的filters
     */
    private String filters;
    /**
     * 慢SQL记录
     */
    private String connectionProperties;
    /**
     * 合并多个DruidDataSource的监控数据
     */
    private boolean useGlobalDataSourceStat;
}
