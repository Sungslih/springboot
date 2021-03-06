package com.xwbing.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2018/5/7 9:56
 * 作者: xiangwb
 * 说明: 分页基础类
 */
@Data
@ApiModel
public class Pagination<T> {
    @ApiModelProperty(value = "当前页")
    private Integer currentPage = 1;
    @ApiModelProperty(value = "每页显示的条数")
    private Integer pageSize = 10;
    @ApiModelProperty(value = "总条数")
    private Long totalNum;
    @ApiModelProperty(value = "是否有下一页")
    private Boolean hasNext;
    @ApiModelProperty(value = "总页数")
    private Integer totalPage;
    @ApiModelProperty(value = "分页结果")
    private List<T> items;

    public Pagination() {
        super();
    }

    public Pagination(PageInfo pageInfo) {
        super();
        this.currentPage = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalNum = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        this.items = (List<T>) JsonUtil.beanToMap(pageInfo.getList());
        this.hasNext = pageInfo.isHasNextPage();
    }

    public Pagination(Page page) {
        super();
        this.currentPage = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.totalNum = page.getTotal();
        this.totalPage = page.getPages();
        this.items = (List<T>) JsonUtil.beanToMap(page);
    }

    public Pagination result(Pagination page, PageInfo pageInfo) {
        page.setTotalNum(pageInfo.getTotal());
        page.setTotalPage(pageInfo.getPages());
        page.setItems((List) JsonUtil.beanToMap(pageInfo.getList()));
        page.setHasNext(pageInfo.isHasNextPage());
        return page;
    }
}
