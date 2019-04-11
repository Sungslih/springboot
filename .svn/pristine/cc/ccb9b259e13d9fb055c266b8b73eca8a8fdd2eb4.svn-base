package com.xwbing.service.sys;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.constant.CommonConstant;
import com.xwbing.constant.CommonEnum;
import com.xwbing.domain.entity.dto.UserDto;
import com.xwbing.domain.entity.model.EmailModel;
import com.xwbing.domain.entity.sys.*;
import com.xwbing.domain.repository.sys.SysUserRepository;
import com.xwbing.exception.BusinessException;
import com.xwbing.rabbit.Sender;
import com.xwbing.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.util.*;

/**
 * 说明: 用户服务层
 * 项目名称: boot-module-demo
 * 创建时间: 2017/5/5 16:44
 * 作者:  xiangwb
 */
@Service
public class SysUserService {
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private SysUserLoginInOutService loginInOutService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysAuthorityService sysAuthorityService;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private Sender sender;

    /**
     * 增
     *
     * @param sysUser
     * @return
     */
    public RestMessage save(SysUser sysUser) {
        RestMessage result = new RestMessage();
        //检查用户名是否存在
        String userName = sysUser.getUserName();
        SysUser old = getByUserName(userName);
        if (old != null) {
            throw new BusinessException("已经存在此用户名");
        }
        String id = PassWordUtil.createId();
        sysUser.setId(id);
        sysUser.setCreateTime(new Date());
        // 获取初始密码
        String[] res = PassWordUtil.getUserSecret(null, null);
        sysUser.setSalt(res[1]);
        sysUser.setPassword(res[2]);
        // 设置为非管理员
        sysUser.setAdmin(CommonEnum.YesOrNoEnum.NO.getCode());
        SysUser one = sysUserRepository.save(sysUser);
        if (one == null) {
            throw new BusinessException("新增用户失败");
        }
        String[] msg = {sysUser.getMail(), userName, res[0]};
        //使用mq发送邮件
        sender.sendEmail(msg);
        result.setSuccess(true);
        result.setId(id);
        return result;
    }

    /**
     * 删
     *
     * @param id
     * @return
     */
    public RestMessage removeById(String id) {
        RestMessage result = new RestMessage();
        //检查该用户是否存在
        SysUser old = getById(id);
        if (old == null) {
            throw new BusinessException("该用户不存在");
        }
        String token = ThreadLocalUtil.getToken();
        String userName = (String) CommonDataUtil.getData(token);
        if (old.getUserName().equals(userName)) {
            throw new BusinessException("不能删除当前登录用户");
        }
        if (CommonEnum.YesOrNoEnum.YES.getCode().equals(old.getAdmin())) {
            throw new BusinessException("不能对管理员进行删除操作");
        }
        //删除用户
        sysUserRepository.delete(id);
        //删除用户角色
        List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(id);
        if (CollectionUtils.isNotEmpty(sysUserRoles)) {
            sysUserRoleService.removeBatch(sysUserRoles);
        }
        result.setMessage("删除成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 更新
     *
     * @param sysUser
     * @return
     */
    public RestMessage update(SysUser sysUser) {
        RestMessage result = new RestMessage();
        //检查该用户是否存在
        SysUser old = getById(sysUser.getId());
        if (old == null) {
            throw new BusinessException("该用户不存在");
        }
        String token = ThreadLocalUtil.getToken();
        String userName = (String) CommonDataUtil.getData(token);
        if (old.getUserName().equals(userName)) {
            throw new BusinessException("不能修改当前登录用户");
        }
        if (CommonEnum.YesOrNoEnum.YES.getCode().equals(old.getAdmin())) {
            throw new BusinessException("不能对管理员进行修改操作");
        }
        // 根据实际情况补充
        old.setName(sysUser.getName());
        old.setMail(sysUser.getMail());
        old.setSex(sysUser.getSex());
        old.setModifiedTime(new Date());
//        old.setUserName(sysUser.getUserName());//用户名不能修改
        SysUser one = sysUserRepository.save(old);
        if (one != null) {
            result.setMessage("更新成功");
            result.setId(sysUser.getId());
            result.setSuccess(true);
        } else {
            result.setMessage("更新失败");
        }
        return result;
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    public SysUser getById(String id) {
        return sysUserRepository.findOne(id);
    }

    /**
     * 根据用户名查找用户
     *
     * @param userName
     * @return
     */
    public SysUser getByUserName(String userName) {
        return sysUserRepository.getByUserName(userName);
    }

    /**
     * 列表查询
     *
     * @return
     */
    public List<SysUser> listAll() {
        List<SysUser> all = sysUserRepository.findAll();
        if (CollectionUtils.isNotEmpty(all)) {
            all.forEach(sysUser -> {
                CommonEnum.SexEnum sexEnum = Arrays.stream(CommonEnum.SexEnum.values()).filter(obj -> obj.getCode().equals(sysUser.getSex())).findFirst().get();
                sysUser.setSexName(sexEnum.getName());
                sysUser.setCreate(DateUtil2.dateToStr(sysUser.getCreateTime(), DateUtil2.YYYY_MM_DD_HH_MM_SS));
                Date modifiedTime = sysUser.getModifiedTime();
                if (modifiedTime != null) {
                    sysUser.setModified(DateUtil2.dateToStr(sysUser.getModifiedTime(), DateUtil2.YYYY_MM_DD_HH_MM_SS));
                }
            });
        }
        return all;
    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    public RestMessage resetPassWord(String id) {
        RestMessage result = new RestMessage();
        SysUser old = getById(id);
        if (old == null) {
            throw new BusinessException("未查询到用户信息");
        }
        String token = ThreadLocalUtil.getToken();
        String userName = (String) CommonDataUtil.getData(token);
        if (old.getUserName().equals(userName)) {
            throw new BusinessException("不能重置当前登录用户");
        }
        if (CommonEnum.YesOrNoEnum.YES.getCode().equals(old.getAdmin())) {
            throw new BusinessException("管理员密码不能重置");
        }
        //生成密码
        String[] str = PassWordUtil.getUserSecret(null, null);
        old.setSalt(str[1]);
        old.setPassword(str[2]);
        old.setModifiedTime(new Date());
        SysUser save = sysUserRepository.save(old);
        if (save == null) {
            throw new BusinessException("重置密码失败");
        }
        //使用mq发送邮件
        String[] msg = {old.getMail(), userName, str[0]};
        sender.sendEmail(msg);
        result.setSuccess(true);
        result.setMessage("重置密码成功");
        return result;
    }

    /**
     * 修改密码
     *
     * @param newPassWord
     * @param oldPassWord
     * @return
     */
    public RestMessage updatePassWord(String newPassWord, String oldPassWord, String id) {
        RestMessage result = new RestMessage();
        SysUser sysUser = getById(id);
        if (sysUser == null) {
            throw new BusinessException("该用户不存在");
        }
        boolean flag = checkPassWord(oldPassWord, sysUser.getPassword(), sysUser.getSalt());
        if (!flag) {
            throw new BusinessException("原密码错误,请重新输入");
        }
        //生成密码
        String[] str = PassWordUtil.getUserSecret(newPassWord, null);
        sysUser.setSalt(str[1]);
        sysUser.setPassword(str[2]);
        sysUser.setModifiedTime(new Date());
        SysUser save = sysUserRepository.save(sysUser);
        if (save != null) {
            result.setMessage("修改密码成功");
            result.setSuccess(true);
        } else {
            result.setMessage("修改密码失败");
        }
        return result;
    }

    /**
     * 登录
     *
     * @param userName
     * @param passWord
     * @param checkCode
     * @return
     */
    public RestMessage login(HttpServletRequest request, String userName, String passWord, String checkCode) {
        RestMessage restMessage = new RestMessage();
        HttpSession session = request.getSession();
        String imgCode = (String) session.getAttribute(CommonConstant.KEY_CAPTCHA);
        //验证验证码
        if (!checkCode.equalsIgnoreCase(imgCode)) {
            throw new BusinessException("验证码错误");
        }
        //验证账号,密码
        SysUser user = getByUserName(userName);
        if (user == null) {
            throw new BusinessException("账号或密码错误");
        }
        boolean check = checkPassWord(passWord, user.getPassword(), user.getSalt());
        if (!check) {
            throw new BusinessException("账号或密码错误");
        }
        //保存登录信息
        SysUserLoginInOut loginInOut = new SysUserLoginInOut();
        loginInOut.setCreateTime(new Date());
        loginInOut.setUserId(user.getId());
        loginInOut.setInoutType(CommonEnum.LoginInOutEnum.IN.getValue());
        String ip = IpUtil.getIpAddr(request);
        loginInOut.setIp(ip);
        RestMessage save = loginInOutService.save(loginInOut);
        if (!save.isSuccess()) {
            throw new BusinessException("保存用户登录日志失败");
        }
        //保存登录数据
        String token = EncodeUtils.urlEncode(RSAUtil.encrypt(userName + "_" + ip));//rsa加密后密文是多行的,所以再次url编码
        CommonDataUtil.setData(token, userName, CommonDataUtil.DAY);
        restMessage.setSuccess(true);
        restMessage.setData(token);
        restMessage.setMessage("登录成功");
        return restMessage;
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    public RestMessage logout(HttpServletRequest request) {
        RestMessage restMessage = new RestMessage();
        String token = ThreadLocalUtil.getToken();
        String userName = (String) CommonDataUtil.getData(token);
        SysUser user = getByUserName(userName);
        if (user != null) {
            //清空缓存数据
            CommonDataUtil.clearData(token);
            //保存登出信息
            SysUserLoginInOut loginInOut = new SysUserLoginInOut();
            loginInOut.setCreateTime(new Date());
            loginInOut.setUserId(user.getId());
            loginInOut.setInoutType(CommonEnum.LoginInOutEnum.OUT.getValue());
            loginInOut.setIp(IpUtil.getIpAddr(request));
            restMessage = loginInOutService.save(loginInOut);
            if (restMessage.isSuccess()) {
                restMessage.setMessage("登出成功");
            } else {
                restMessage.setMessage("保存用户登出信息失败");
            }
        } else {
            restMessage.setMessage("没有获取到用户登录信息,请重新登录");
        }
        return restMessage;
    }

    /**
     * 根据用户主键，是否有效，查找所拥有的权限
     *
     * @param userId
     * @param enable
     * @return
     */
    public List<SysAuthority> listAuthorityByIdAndEnable(String userId, String enable) {
        List<SysAuthority> list = new ArrayList<>();
        //根据用戶id和是否启用获取获取角色
        List<SysRole> sysRoles = sysRoleService.listByUserIdEnable(userId, enable);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return list;
        }
        //遍历获取每个角色拥有的权限，并去重
        List<SysAuthority> temp;
        for (SysRole sysRole : sysRoles) {
            temp = sysAuthorityService.listByRoleIdEnable(sysRole.getId(), enable);
            if (CollectionUtils.isNotEmpty(temp)) {
                for (SysAuthority auth : temp) {
                    if (list.contains(auth)) {
                        continue;// 如果存在，那么去除
                    }
                    list.add(auth);
                }
            }
        }
        return list;
    }

    /**
     * 导出用户信息Excel
     */
    public void exportReport(HttpServletResponse response) {
        String fileName = CommonConstant.USER_REPORT_FILE_NAME;//文件名
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
            fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// 指定下载的文件名
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            List<UserDto> listDto = listReport();//内容list
            if (CollectionUtils.isEmpty(listDto)) {
                return;
            }
            List<String[]> list = ExcelUtil.convert2List(listDto);
            String title = CommonConstant.USER_REPORT_FILE_NAME;
            String[] columns = CommonConstant.USER_REPORT_COLUMNS;
            bufferedOutPut.flush();
            HSSFWorkbook wb = ExcelUtil.Export(title, columns, list);
            wb.write(bufferedOutPut);
            bufferedOutPut.close();
        } catch (Exception e) {
            throw new BusinessException("导出excel错误");
        }
    }

    /**
     * 获取excel导出列表所需数据
     *
     * @return
     */
    private List<UserDto> listReport() {
        List<UserDto> listDto = new ArrayList<>();
        List<SysUser> list = sysUserRepository.findAll();
        if (CollectionUtils.isNotEmpty(list)) {
            UserDto temp;
            for (SysUser info : list) {
                temp = new UserDto();
                temp.setIsAdmin(CommonEnum.YesOrNoEnum.YES.getCode().equals(info.getAdmin()) ? "是" : "否");
                temp.setMail(info.getMail());
                temp.setName(info.getName());
                temp.setSex(Integer.valueOf(info.getSex()) == 1 ? "男" : "女");
                temp.setUserName(info.getUserName());
                listDto.add(temp);
            }
        }
        return listDto;
    }

    /**
     * 校验密码
     *
     * @param realPassWord
     * @param passWord
     * @param salt
     * @return
     */
    private boolean checkPassWord(String passWord, String realPassWord, String salt) {
        // 根据密码盐值 解码
        byte[] saltByte = EncodeUtils.hexDecode(salt);
        byte[] hashPassword = DigestsUtil.sha1(passWord.getBytes(), saltByte, PassWordUtil.HASH_INTERATIONS);
        // 密码 数据库中密码
        String validatePassWord = EncodeUtils.hexEncode(hashPassword);
        //判断密码是否相同
        return realPassWord.equals(validatePassWord);
    }

    /**
     * 发送邮件
     *
     * @param email
     * @param userName
     * @param passWord
     * @return
     */
    private boolean sendEmail(String email, String userName, String passWord) {
        SysConfig sysConfig = sysConfigService.getByCode(CommonConstant.EMAIL_KEY);
        if (sysConfig == null) {
            throw new BusinessException("没有查找到邮件配置项");
        }
        JSONObject jsonObject = JSONObject.parseObject(sysConfig.getValue());
        EmailModel emailModel = JSONObject.toJavaObject(jsonObject, EmailModel.class);
        emailModel.setToEmail(email);
        emailModel.setSubject(emailModel.getSubject());
        emailModel.setCentent(emailModel.getCentent() + " 你的用户名是:" + userName + ",密码是:" + passWord);
        return EmailUtil.sendTextEmail(emailModel);
    }
}
