package com.ideage.ams.service;


import com.ideage.ams.entity.AdminUser;
import com.ideage.ams.utils.PageResult;
import com.ideage.ams.utils.PageUtil;

public interface AdminUserService {

    PageResult getAdminUserPage(PageUtil pageUtil);

    AdminUser updateTokenAndLogin(String userName, String password);

    AdminUser selectById(Long id);

    AdminUser selectByUserName(String userName);

    int save(AdminUser user);

    int updatePassword(AdminUser user);

    int deleteBatch(Integer[] ids);

    AdminUser getAdminUserByToken(String userToken);
}
