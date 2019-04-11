package com.xwbing.service.sys;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xwbing.constant.CommonConstant;
import com.xwbing.domain.entity.sys.SysRole;
import com.xwbing.domain.entity.sys.SysRoleAuthority;
import com.xwbing.domain.entity.sys.SysUserRole;
import com.xwbing.domain.mapper.sys.SysRoleMapper;
import com.xwbing.exception.BusinessException;
import com.xwbing.util.Pagination;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2017/11/14 9:24
 * 作者: xiangwb
 * 说明: 角色服务层
 */
@Service
public class SysRoleService {
    @Resource
    private SysRoleAuthorityService roleAuthorityService;
    @Resource
    private SysUserRoleService userRoleService;
    @Resource
    private SysRoleMapper roleMapper;

    /**
     * 保存角色
     *
     * @param sysRole
     * @return
     */
    public RestMessage save(SysRole sysRole) {
        RestMessage result = new RestMessage();
        //检查编码
        boolean b = uniqueCode(sysRole.getCode(), null);
        if (!b) {
            throw new BusinessException("该编码已存在");
        }
        String id = PassWordUtil.createId();
        sysRole.setId(id);
        int save = roleMapper.insert(sysRole);
        if (save == 1) {
            result.setSuccess(true);
            result.setId(id);
            result.setMessage("保存角色成功");
        } else {
            result.setMessage("保存角色失败");
        }
        return result;
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @Transactional
    public RestMessage removeById(String id) {
        RestMessage result = new RestMessage();
        SysRole one = getById(id);
        if (one == null) {
            throw new BusinessException("该角色不存在");
        }
        //删除角色
        roleMapper.deleteById(id);
        //删除角色权限
        List<SysRoleAuthority> roleAuthorities = roleAuthorityService.listByRoleId(id);
        if (CollectionUtils.isNotEmpty(roleAuthorities)) {
            List<String> ids = roleAuthorities.stream().map(SysRoleAuthority::getId).collect(Collectors.toList());
            roleAuthorityService.removeBatch(ids);
        }
        result.setMessage("删除成功");
        result.setSuccess(true);
        return result;
    }

    /**
     * 修改角色
     *
     * @param sysRole
     * @return
     */
    public RestMessage update(SysRole sysRole) {
        RestMessage result = new RestMessage();
        String id = sysRole.getId();
        SysRole old = getById(id);
        if (old == null) {
            throw new BusinessException("该角色不存在");
        }
        //检查编码
        boolean b = uniqueCode(sysRole.getCode(), id);
        if (!b) {
            throw new BusinessException("该编码已存在");
        }
        old.setName(sysRole.getName());
        old.setCode(sysRole.getCode());
        old.setEnable(sysRole.getEnable());
        old.setRemark(sysRole.getRemark());
        int update = roleMapper.update(old);
        if (update == 1) {
            result.setSuccess(true);
            result.setId(id);
            result.setMessage("修改角色成功");
        } else {
            result.setMessage("修改角色失败");
        }
        return result;
    }

    /**
     * 根据主键查找
     *
     * @param id
     * @return
     */
    public SysRole getById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        } else {
            return roleMapper.findById(id);
        }
    }

    /**
     * 根据是否启用查询
     *
     * @param enable
     * @return
     */
    public Pagination pageByEnable(String enable, Pagination page) {
        Map<String, Object> map = new HashMap<>();
        map.put("enable", enable);
        PageInfo<SysRole> pageInfo = PageHelper.startPage(page.getCurrentPage(), page.getPageSize()).doSelectPageInfo(() -> roleMapper.find(map));
        return page.result(page, pageInfo);
    }

    /**
     * 根据用户主键，是否启用状态查询角色列表
     *
     * @param userId
     * @param enable
     * @return
     */
    public List<SysRole> listByUserIdEnable(String userId, String enable) {
        List<SysRole> list = new ArrayList<>();
        //从用户角色表中获取所有该用户id的角色
        List<SysUserRole> sysUserRoles = userRoleService.listByUserId(userId);
        if (sysUserRoles == null) {
            return list;
        }
        //根据角色id获取对应角色列表
        List<String> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(roleIds)) {
            Map<String, Object> map = new HashMap<>();
            map.put("ids", roleIds);
            if (StringUtils.isNotEmpty(enable)) {
                map.put("enable", CommonConstant.IS_ENABLE);
            }
            list = roleMapper.find(map);
        }
        return list;
    }

    /**
     * 检查code是否唯一 true唯一
     *
     * @param code
     * @param id
     * @return
     */
    private boolean uniqueCode(String code, String id) {
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException("code不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        if (StringUtils.isNotEmpty(id)) {
            map.put("id", id);
        }
        List<SysRole> sysRoles = roleMapper.find(map);
        return sysRoles.size() == 0;
    }
}
