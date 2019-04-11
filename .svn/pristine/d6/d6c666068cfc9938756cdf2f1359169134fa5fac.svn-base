package com.xwbing.controller.rest;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.domain.entity.vo.RestMessageVo;
import com.xwbing.service.rest.CommonService;
import com.xwbing.util.JsonResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2018/5/7 9:57
 * 作者: xiangwb
 * 说明:
 */
@Api(tags = "commonApi", description = "公共相关接口")
@RestController
@RequestMapping("/common/")
public class CommonControl {
    @Resource
    private CommonService commonService;

    @LogInfo("获取签名")
    @ApiOperation(value = "获取签名", response = RestMessageVo.class)
    @GetMapping("getSign")
    public JSONObject getSign(HttpServletRequest request) {
        String sign = commonService.getSign(request);
        return JsonResult.toJSONObj(sign, "");
    }

    @LogInfo("上传文件")
    @PostMapping("upload")
    public JSONObject upload(@ApiParam(value = "文件", required = true) MultipartFile file) {
        RestMessage restMessage = commonService.upload(file);
        return JsonResult.toJSONObj(restMessage);
    }
}

