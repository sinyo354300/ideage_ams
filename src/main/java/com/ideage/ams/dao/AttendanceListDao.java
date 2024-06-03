package com.ideage.ams.dao;


import com.ideage.ams.entity.AttendanceList;

import java.util.List;
import java.util.Map;


public interface AttendanceListDao {

    List<AttendanceList> findAttendanceList(Map<String, Object> map);

    int getTotalAttendanceList(Map<String, Object> map);

    int insertAttendanceList(AttendanceList attendanceList);

    int updAttendanceList(AttendanceList attendanceList);

    int delAttendanceList(Integer id);

    AttendanceList getAttendanceListById(Integer id);

    int deleteBatch(Object[] id);
}
