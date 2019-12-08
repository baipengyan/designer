package com.gao.designer.dao;

import com.gao.designer.entity.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> findPermissionListByRoleId(int roleId);
}
