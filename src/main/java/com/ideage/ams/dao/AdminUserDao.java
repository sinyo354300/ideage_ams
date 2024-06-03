package com.ideage.ams.dao;


import com.ideage.ams.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface AdminUserDao {


    List<AdminUser> findAdminUsers(Map param);

    int getTotalAdminUser(Map param);

    AdminUser getAdminUserByUserNameAndPassword(@Param("userName") String userName, @Param("passwordMD5") String passwordMD5);

    AdminUser getAdminUserByToken(String userToken);

    AdminUser getAdminUserById(Long id);

    AdminUser getAdminUserByUserName(String userName);

    int addUser(AdminUser user);

    int insertUsersBatch(@Param("adminUsers") List<AdminUser> adminUsers);

    int updateUserPassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    int updateUserToken(@Param("userId") Long userId, @Param("newToken") String newToken);

    int deleteBatch(Object[] ids);

    List<AdminUser> getAllAdminUsers();
}
