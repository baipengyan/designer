package com.gao.designer.dao;

import com.gao.designer.entity.Role;
import com.gao.designer.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    /**
            *获取所有用户
     * @return
             */
    List<User> selectAll();

    /**
     * 删除用户
     * @param uid
     * @return
     */
    int delUser(long uid);

    /**
     *添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据用户手机号查询
     * @param moibble
     * @return
     */
    String selectByMoible(String moibble);

    /**
     * 根据用户名称进行查询
     * @param username
     * @return
     */
    User findByUsername(String username);

}
