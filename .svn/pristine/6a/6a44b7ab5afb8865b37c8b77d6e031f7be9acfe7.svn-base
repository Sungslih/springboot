package com.xwbing.service.rest;

import com.xwbing.util.DigestsUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/5/7 8:59
 * 作者: xiangwb
 * 说明:
 */
@Service
public class CommonService {
    /**
     * 保存信息表单提交时获取校验签名
     *
     * @param request
     * @return
     */
    public String getSign(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sign = DigestsUtil.getSign();
        session.setAttribute("sign", sign);
        return sign;
    }
}
