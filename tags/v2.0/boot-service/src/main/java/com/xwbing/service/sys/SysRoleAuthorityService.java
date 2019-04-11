package com.xwbing.service.sys;

import com.xwbing.domain.entity.sys.SysRoleAuthority;
import com.xwbing.domain.mapper.sys.SysRoleAuthorityMapper;
import com.xwbing.util.RestMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2017/11/14 15:15
 * 作者: xiangwb
 * 说明: 角色权限服务层
 */
@Service
public class SysRoleAuthorityService {
    @Resource
    private SysRoleAuthorityMapper sysRoleAuthorityMapper;

    /**
     * 执行用户角色权限保存操作,保存之前先判断是否存在，存在删除
     *
     * @param list
     * @param roleId
     * @return
     */
    @Transactional
    public RestMessage saveBatch(List<SysRoleAuthority> list, String roleId) {
        RestMessage result = new RestMessage();
        //获取角色原有权限
        List<SysRoleAuthority> roleAuthorities = listByRoleId(roleId);
        //删除原有权限
        if (CollectionUtils.isNotEmpty(roleAuthorities)) {
            List<String> ids = roleAuthorities.stream().map(SysRoleAuthority::getId).collect(Collectors.toList());
            sysRoleAuthorityMapper.deleteByIds(ids);
        }
        //新增角色权限
        int save = sysRoleAuthorityMapper.insertBatch(list);
        if (save != 0) {
            result.setSuccess(true);
            result.setMessage("保存角色权限成功");
        } else {
            result.setMessage("保存角色权限失败");
        }
        return result;
    }


    /**
     * 根据角色主键获取
     *
     * @param roleId
     * @return
     */
    public List<SysRoleAuthority> listByRoleId(String roleId) {
        if (StringUtils.isEmpty(roleId)) {
            return Collections.EMPTY_LIST;
        } else {
            return sysRoleAuthorityMapper.findByRoleId(roleId);
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public RestMessage removeBatch(List<String> ids) {
        RestMessage result = new RestMessage();
        int deleteByIds = sysRoleAuthorityMapper.deleteByIds(ids);
        if (deleteByIds != 0) {
            result.setSuccess(true);
            result.setMessage("批量删除成功");
        } else {
            result.setMessage("批量删除失败");
        }
        return result;
    }
}

