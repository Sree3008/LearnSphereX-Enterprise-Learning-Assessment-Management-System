package com.learnspherex.batch.service;

import java.util.List;

import com.learnspherex.batch.entity.Attendance;

public interface AttendanceService {

    Attendance createAttendance(Attendance attendance);

    List<Attendance> getAllAttendance();

    List<Attendance> getAttendanceByBatchId(Long batchId);

    Attendance getAttendanceById(Long id);

    Attendance updateAttendance(Long id, Attendance attendance);

    void deleteAttendance(Long id);
}