package com.xwbing.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.domain.entity.sys.SysConfig;
import com.xwbing.domain.entity.vo.ListSysConfigVo;
import com.xwbing.domain.entity.vo.RestMessageVo;
import com.xwbing.domain.entity.vo.SysConfigVo;
import com.xwbing.service.sys.SysConfigService;
import com.xwbing.util.JsonResult;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 说明: 系统配置控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/10 16:36
 * 作者:  xiangwb
 */
@Slf4j
@Api(tags = "configApi", description = "系统配置相关接口")
@RestController
@RequestMapping("/config/")
public class SysConfigControl {
    @Resource
    private SysConfigService sysConfigService;

    @ApiOperation(value = "新增", notes = "新增系统配置信息", response = RestMessageVo.class)
    @PostMapping("save")
    public JSONObject save(@RequestBody @Valid SysConfig sysConfig) {
        String logMsg = "新增系统配置信息";
        log.info(logMsg);
        RestMessage result = sysConfigService.save(sysConfig);
        return JsonResult.toJSONObj(result);
    }

    @ApiOperation(value = "删除", notes = "根据code删除系统配置信息", response = RestMessageVo.class)
    @DeleteMapping("removeByCode/{code}")
    public JSONObject removeByCode(@PathVariable String code) {
        String logMsg = "根据code删除系统配置信息";
        log.info(logMsg + " code:{}", code);
        if (StringUtils.isEmpty(code)) {
            return JsonResult.toJSONObj("code不能为空");
        }
        RestMessage result = sysConfigService.removeByCode(code);
        return JsonResult.toJSONObj(result);
    }

    @ApiOperation(value = "修改", notes = "修改系统配置信息", response = RestMessageVo.class)
    @PutMapping("update")
    public JSONObject update(@RequestBody @Valid SysConfig sysConfig) {
        String logMsg = "修改系统配置信息";
        log.info(logMsg);
        if (StringUtils.isEmpty(sysConfig.getCode())) {
            return JsonResult.toJSONObj("配置项的code不能为空");
        }
        RestMessage result = sysConfigService.update(sysConfig);
        return JsonResult.toJSONObj(result);
    }

    @ApiOperation(value = "查找", notes = "根据key查找系统配置信息", response = SysConfigVo.class)
    @GetMapping("getByCode")
    public JSONObject getByCode(@RequestParam String code) {
        String logMsg = "根据code查找系统配置信息";
        log.info(logMsg + " code:{}", code);
        if (StringUtils.isEmpty(code)) {
            return JsonResult.toJSONObj("code不能为空");
        }
        SysConfig one = sysConfigService.getByCode(code);
        if (one == null) {
            return JsonResult.toJSONObj("该配置不存在");
        }
        return JsonResult.toJSONObj(one, "");
    }

    @ApiOperation(value = "查找列表", notes = "根据是否启用查找配置列表", response = ListSysConfigVo.class)
    @GetMapping("listByEnable")
    public JSONObject listByEnable(@RequestParam String enable) {
        String logMsg = "根据是否启用查找配置列表";
        log.info(logMsg + " enable:{}", enable);
        if (StringUtils.isEmpty(enable)) {
            return JsonResult.toJSONObj("是否启用不能为空");
        }
        List<SysConfig> byEnable = sysConfigService.listByEnable(enable);
        return JsonResult.toJSONObj(byEnable, "");
    }
}
