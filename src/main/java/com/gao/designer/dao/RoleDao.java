package com.gao.designer.dao;

import com.gao.designer.entity.Role;

import java.util.List;

public interface RoleDao {

    List<Role> findRoleListByUserId(int userId);
}
