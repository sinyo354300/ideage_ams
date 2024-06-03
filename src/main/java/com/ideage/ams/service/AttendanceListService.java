package com.ideage.ams.service;

import com.ideage.ams.utils.PageResult;
import com.ideage.ams.utils.PageUtil;
import com.ideage.ams.entity.AttendanceList;

import java.util.List;
import java.util.Map;

public interface AttendanceListService {
    PageResult getAttendanceListPage(PageUtil pageUtil);

    AttendanceList queryObject(Integer id);

    List<AttendanceList> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    int save(AttendanceList attendanceList);

    int update(AttendanceList attendanceList);

    int delete(Integer id);

    int deleteBatch(Integer[] ids);
}
