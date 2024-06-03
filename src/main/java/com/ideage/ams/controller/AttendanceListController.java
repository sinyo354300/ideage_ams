package com.ideage.ams.controller;


import com.ideage.ams.common.Constants;
import com.ideage.ams.common.MsgConstants;
import com.ideage.ams.common.Result;
import com.ideage.ams.common.ResultGenerator;
import com.ideage.ams.config.annotation.TokenToUser;
import com.ideage.ams.entity.AdminUser;
import com.ideage.ams.entity.AttendanceList;
import com.ideage.ams.service.AttendanceListService;
import com.ideage.ams.utils.MassageUtil;
import com.ideage.ams.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description 勤怠管理・コントローラー
 * @author zhen.cheng
 */
@RestController
@RequestMapping("/attendancelist")
public class AttendanceListController {

    @Autowired
    private AttendanceListService attendanceListService;

    /**
     * リストの取得
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR,  MsgConstants.MSG_ERROR_0001);
        }
        //查询列表数据
        PageUtil pageUtil = new PageUtil(params);
        return ResultGenerator.getSuccessResult(attendanceListService.getAttendanceListPage(pageUtil));
    }

    /**
     * 詳細情報
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public Result info(@PathVariable("id") Integer id) {
        AttendanceList attendanceList = attendanceListService.queryObject(id);
        return ResultGenerator.getSuccessResult(attendanceList);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody AttendanceList attendanceList, @TokenToUser AdminUser loginUser) {
        if (attendanceList.getAddName()==null){
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR,  MassageUtil.format(MsgConstants.DEFAULT_MESSAGE_0007, "ユーザー名"));
        }
        if (loginUser == null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_NOT_LOGIN, MsgConstants.DEFAULT_MESSAGE_0005);
        }
        if (attendanceListService.save(attendanceList) > 0) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult("添加失败");
        }
    }

    /**
     * 編集
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody AttendanceList attendanceList, @TokenToUser AdminUser loginUser) {
        if (loginUser == null) {
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_NOT_LOGIN, MsgConstants.DEFAULT_MESSAGE_0005);
        }
        if (attendanceListService.update(attendanceList) > 0) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult( MsgConstants.DEFAULT_MESSAGE_0002);
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
            return ResultGenerator.getErrorResult(Constants.RESULT_CODE_PARAM_ERROR, MsgConstants.DEFAULT_MESSAGE_0002);
        }
        if (attendanceListService.deleteBatch(ids) > 0) {
            return ResultGenerator.getSuccessResult();
        } else {
            return ResultGenerator.getFailResult(MsgConstants.DEFAULT_MESSAGE_0002);
        }
    }

}
