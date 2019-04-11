package com.xwbing.controller.rest;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.domain.entity.ExpressInfo;
import com.xwbing.domain.entity.vo.ExpressInfoVo;
import com.xwbing.rabbit.Sender;
import com.xwbing.redis.RedisService;
import com.xwbing.service.rest.CookieSessionService;
import com.xwbing.service.rest.ExpressDeliveryService;
import com.xwbing.service.rest.QRCodeZipService;
import com.xwbing.util.HttpUtil;
import com.xwbing.util.JsonResult;
import com.xwbing.util.RSAUtil;
import com.xwbing.util.RestMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 说明: 测试控制层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 9:21
 * 作者:  xiangwb
 */
@Slf4j
@Api(tags = "testApi", description = "测试相关接口")
@RestController
@RequestMapping("/test/")
public class TestControl {
    @Resource
    private RedisService redisService;
    @Resource
    private ExpressDeliveryService expressDeliveryService;
    @Resource
    private QRCodeZipService qrCodeZipService;
    @Resource
    private CookieSessionService cookieSessionService;
    @Resource
    private Sender sender;

    @GetMapping("send")
    public void sendM(@RequestParam String[] msg) {
        sender.sendM(msg);
    }

    @LogInfo("redis功能测试")
    @GetMapping("redis")
    public void redis() {
        redisService.set("redis", "xwbing");
        String s = redisService.get("redis");
        log.info("redis获取的数据为:" + s);
    }

    @LogInfo("log功能测试")
    @GetMapping("log")
    public void log() {
        log.info("info test");
        log.error("error test");
    }

    @LogInfo("获取快递列表")
    @GetMapping("listShipperCode")
    public JSONObject listShipperCode() {
        List<JSONObject> list = expressDeliveryService.listShipperCode();
        return JsonResult.toJSONObj(list, "");
    }

    @LogInfo("快递查询")
    @PostMapping("expressInfo")
    public JSONObject getExpressInfo(@RequestBody ExpressInfo info) {
        if (StringUtils.isEmpty(info.getLogisticCode()) || StringUtils.isEmpty(info.getShipperCode())) {
            return JsonResult.toJSONObj("快递公司或物流单号不能为空");
        }
        ExpressInfoVo infoVo = expressDeliveryService.queryOrderTraces(info);
        return JsonResult.toJSONObj(infoVo, "查询快递信息成功");
    }

    @LogInfo("生成二维码")
    @PostMapping("createQRCode")
    public JSONObject createQRCode(@RequestParam String name, @RequestParam String text) {
        RestMessage qrCode = qrCodeZipService.createQRCode(name, text);
        return JsonResult.toJSONObj(qrCode);
    }

    @LogInfo("解析二维码")
    @GetMapping("decode")
    public JSONObject decode(@RequestParam String path) {
        if (StringUtils.isEmpty(path)) {
            return JsonResult.toJSONObj("二维码图片路径不能为空");
        }
        File file = new File(path);
        RestMessage decode = qrCodeZipService.decode(file);
        return JsonResult.toJSONObj(decode);
    }

    @LogInfo("导出zip")
    @GetMapping("batchGetImage")
    public JSONObject batchGetImage(HttpServletResponse response, @RequestParam String[] names, @RequestParam String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return JsonResult.toJSONObj("zip名称不能为空");
        }
        RestMessage restMessage = qrCodeZipService.batchGetImage(response, names, fileName);
        return JsonResult.toJSONObj(restMessage);
    }

    @LogInfo("rsa")
    @GetMapping("rsa")
    public void rsa() {
        String en = RSAUtil.encrypt("123456");
        String de = RSAUtil.decrypt(en);
        System.out.println(de);
    }

    @LogInfo("httpUtil")
    @PostMapping("httpUtil")
    public JSONObject httpUtil(@RequestBody JSONObject param) {
//        String url = "http://localhost:8080/user/listAll";
//        return HttpClientUtil.get(url);
        String url = "http://localhost:8080/user/save";
        return HttpUtil.postByJson(url, param);
    }

    @LogInfo("session")
    @GetMapping("session")
    public JSONObject session(HttpServletRequest request) {
        return JsonResult.toJSONObj(cookieSessionService.session(request));
    }

    @LogInfo("cookie")
    @GetMapping("cookie")
    public JSONObject cookie(HttpServletRequest request, HttpServletResponse response) {
        return JsonResult.toJSONObj(cookieSessionService.cookie(response, request));
    }

    @LogInfo("completableFuture")
    @GetMapping("completableFuture")
    public JSONObject completableFuture() {
        return JsonResult.toJSONObj(thenCombine(), "");
    }

    private JSONObject thenCombine() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //出现异常,异常会被限制在执行任务的线程范围内
            if (1 == 1) {
                throw new RuntimeException("545456");
            }
            return "world";
        }), (s1, s2) -> {
            JSONObject object = new JSONObject();
            object.put("s1", s1);
            object.put("s2", s2);
            return object;
        }).join();
    }
}
