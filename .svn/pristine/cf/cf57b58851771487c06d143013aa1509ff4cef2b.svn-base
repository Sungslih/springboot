package com.xwbing.domain.entity.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 说明: 权限
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "sys_authority")
public class SysAuthority extends BaseEntity {
    private static final long serialVersionUID = -6469518352117371987L;
    public static String table = "sys_authority";
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    @ApiModelProperty(value = "编码", required = true)
    private String code;
    @ApiModelProperty(value = "是否启用(Y|N)", example = "Y", required = true)
    private String enable;
    @ApiModelProperty(value = "url地址", required = true)
    private String url;
    @ApiModelProperty(value = "父ID")
    @Column(name = "parent_id")
    private String parentId;
    @ApiModelProperty(value = "类型(菜单:1|按钮:2)", example = "1", required = true)
    private Integer type;
    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;
}
