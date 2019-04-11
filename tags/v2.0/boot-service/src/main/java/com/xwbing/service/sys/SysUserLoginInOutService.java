package com.xwbing.service.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwbing.constant.CommonEnum;
import com.xwbing.domain.entity.sys.SysUserLoginInOut;
import com.xwbing.domain.mapper.sys.SysUserLoginInOutMapper;
import com.xwbing.util.DateUtil2;
import com.xwbing.util.Pagination;
import com.xwbing.util.RestMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2017/11/7 9:56
 * 作者: xiangwb
 * 说明: 用户登录登出服务层
 */
@Service
public class SysUserLoginInOutService {
    public static final int[] ITEM = {1, 2};
    @Resource
    private SysUserLoginInOutMapper loginInOutMapper;

    /**
     * 保存
     *
     * @param inOut
     * @return
     */
    public RestMessage save(SysUserLoginInOut inOut) {
        RestMessage result = new RestMessage();
        int save = loginInOutMapper.insert(inOut);
        if (save == 1) {
            result.setSuccess(true);
            result.setMessage("保存登录登出信息成功");
        } else {
            result.setMessage("保存登录登出信息失败");
        }
        return result;
    }

    /**
     * 根据类型分页查询
     *
     * @param inout
     * @return
     */
    public Pagination page(Integer inout, String startDate, String endDate, Pagination page) {
        Map<String, Object> map = new HashMap<>();
        if (inout != null) {
            map.put("inout", inout);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        PageInfo<SysUserLoginInOut> pageInfo = PageHelper.startPage(page.getCurrentPage(), page.getPageSize()).doSelectPageInfo(() -> loginInOutMapper.findByInoutType(map));
        List<SysUserLoginInOut> list = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(loginInOut -> {
                //登录登出
                CommonEnum.LoginInOutEnum inOutEnum = Arrays.stream(CommonEnum.LoginInOutEnum.values()).filter(obj -> obj.getValue() == loginInOut.getInoutType()).findFirst().get();
                loginInOut.setInoutTypeName(inOutEnum.getName());
            });
        }
        return page.result(page, pageInfo);
    }

    /**
     * 登录登出饼图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public JSONArray pie(String startDate, String endDate) {
        JSONArray result = new JSONArray();
        //获取数据
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        List<SysUserLoginInOut> list = loginInOutMapper.countByType(map);
        //统计数据
        Map<Integer, List<SysUserLoginInOut>> collect = list.stream().collect(Collectors.groupingBy(SysUserLoginInOut::getInoutType));
        JSONObject obj;
        for (int item : ITEM) {
            obj = new JSONObject();
            String name = Arrays.stream(CommonEnum.LoginInOutEnum.values()).filter(login -> login.getValue() == item).findFirst().get().getName();
            obj.put("name", name);
            List<SysUserLoginInOut> sample = collect.get(item);
            if (sample != null) {
                int sum = sample.get(0).getCount();
                obj.put("value", sum);
            } else {
                obj.put("value", 0);
            }
            result.add(obj);
        }
        return result;
    }

    /**
     * 登录登出柱状图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<String, Object> bar(String startDate, String endDate) {
        Map<String, Object> result = new HashMap<>();
        //统计日期
        List<String> days = DateUtil2.listDate(startDate, endDate);
        result.put("xAxis", days);
        //获取数据
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(startDate)) {
            map.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            map.put("endDate", endDate + " 23:59:59");
        }
        List<SysUserLoginInOut> list = loginInOutMapper.findByInoutType(map);
        //统计数据
        Map<String, List<SysUserLoginInOut>> collect = list.stream().peek(loginInOut -> loginInOut.setRecordTime(loginInOut.getRecordTime().substring(0, 10))).collect(Collectors.groupingBy(SysUserLoginInOut::getRecordTime));
        JSONObject obj;
        JSONArray array;
        JSONArray series = new JSONArray();
        for (int item : ITEM) {
            obj = new JSONObject();
            String name = Arrays.stream(CommonEnum.LoginInOutEnum.values()).filter(login -> login.getValue() == item).findFirst().get().getName();
            obj.put("name", name);
            array = new JSONArray();
            for (String day : days) {
                List<SysUserLoginInOut> sample = collect.get(day);
                if (sample != null) {
                    int sum = (int) sample.stream().filter(it -> item == it.getInoutType()).count();
                    array.add(sum);
                } else {
                    array.add(0);
                }
            }
            obj.put("data", array);
            series.add(obj);
        }
        result.put("series", series);
        return result;
    }
}
