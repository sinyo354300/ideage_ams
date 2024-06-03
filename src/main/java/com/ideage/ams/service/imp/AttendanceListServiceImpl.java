package com.ideage.ams.service.imp;


import com.ideage.ams.dao.AttendanceListDao;
import com.ideage.ams.service.AttendanceListService;
import com.ideage.ams.utils.PageResult;
import com.ideage.ams.utils.PageUtil;
import com.ideage.ams.entity.AttendanceList;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("attendancelistService")
public class AttendanceListServiceImpl implements AttendanceListService {

    @Resource
    private AttendanceListDao attendanceListDao;

    @Override
    public PageResult getAttendanceListPage(PageUtil pageUtil) {
        List<AttendanceList> attendanceList = attendanceListDao.findAttendanceList(pageUtil);
        int total = attendanceListDao.getTotalAttendanceList(pageUtil);
        PageResult pageResult = new PageResult(attendanceList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public AttendanceList queryObject(Integer id) {
    	AttendanceList attendanceList = attendanceListDao.getAttendanceListById(id);
        if (attendanceList != null) {
            return attendanceList;
        }
        return null;
    }

    @Override
    public List<AttendanceList> queryList(Map<String, Object> map) {
        List<AttendanceList> articles = attendanceListDao.findAttendanceList(map);
        return articles;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return attendanceListDao.getTotalAttendanceList(map);
    }

    @Override
    public int save(AttendanceList attendanceList) {
        return attendanceListDao.insertAttendanceList(attendanceList);
    }

    @Override
    public int update(AttendanceList attendanceList) {
        attendanceList.setUpdateTime(new Date());
        return attendanceListDao.updAttendanceList(attendanceList);
    }

    @Override
    public int delete(Integer id) {
        return attendanceListDao.delAttendanceList(id);
    }

    @Override
    public int deleteBatch(Integer[] ids) {
        return attendanceListDao.deleteBatch(ids);
    }
}
