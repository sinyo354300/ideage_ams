package com.ideage.ams.controller;

import com.ideage.ams.common.Constants;
import com.ideage.ams.common.MsgConstants;
import com.ideage.ams.common.Result;
import com.ideage.ams.common.ResultGenerator;
import com.ideage.ams.config.annotation.TokenToUser;
import com.ideage.ams.entity.AdminUser;
import com.ideage.ams.service.AdminUserService;
import com.ideage.ams.utils.MassageUtil;
import com.ideage.ams.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description ユーザー管理・コントローラー
 * @author zhen.cheng
 */
@RestController
@RequestMapping("/users")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * ユーザーマスタの取得
     */
    @SuppressWarnings("deprecation")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.MSG_ERROR_0001);
        }
        //ユーザーマスタの取得
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.getSuccessResult(adminUserService.getAdminUserPage(pageUtil));
    }
    
    /**
     * ログイン実行処理
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody AdminUser user) {
        Result result = ResultGenerator.getFailResult(MsgConstants.DEFAULT_MESSAGE_0004);
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
        	return result;
        }
        AdminUser loginUser = adminUserService.updateTokenAndLogin(user.getUserName(), user.getPassword());
        if (loginUser != null) {
            result = ResultGenerator.getSuccessResult(loginUser);
        }
        return result;
    }

    /**
     * 保存ボタン押下処理_ユーザーアカウント新規登録
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody AdminUser user, @TokenToUser AdminUser loginUser) {
        if (loginUser == null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_NOT_LOGIN, MsgConstants.DEFAULT_MESSAGE_0005);
        }
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.DEFAULT_MESSAGE_0004);
        }
        AdminUser tempUser = adminUserService.selectByUserName(user.getUserName());
        if (tempUser != null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, 
            		MassageUtil.format(MsgConstants.DEFAULT_MESSAGE_0006, "ユーザー名"));
        }

        if (adminUserService.save(user) > 0) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR,MsgConstants.DEFAULT_MESSAGE_0002);
        }
    }

    /**
     * 保存ボタン押下処理_ユーザーアカウント更新
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public Result update(@RequestBody AdminUser user, @TokenToUser AdminUser loginUser) {
        if (loginUser == null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_NOT_LOGIN, MsgConstants.DEFAULT_MESSAGE_0005);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.DEFAULT_MESSAGE_0004);
        }
        AdminUser tempUser = adminUserService.selectById(user.getId());
        if (tempUser == null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.DEFAULT_MESSAGE_0004);
        }
        if ("admin".endsWith(tempUser.getUserName().trim())) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.DEFAULT_MESSAGE_0004);
        }
        tempUser.setPassword(user.getPassword());
        if (adminUserService.updatePassword(user) > 0) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult(MsgConstants.DEFAULT_MESSAGE_0002);
        }
    }

    /**
     * 削除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(@RequestBody Integer[] ids, @TokenToUser AdminUser loginUser) {
        if (loginUser == null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_NOT_LOGIN, MsgConstants.DEFAULT_MESSAGE_0005);
        }
        if (ids.length < 1) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.DEFAULT_MESSAGE_0004);
        }
        if (adminUserService.deleteBatch(ids) > 0) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult(MsgConstants.DEFAULT_MESSAGE_0002);
        }
    }
}