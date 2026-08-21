package com.learnspherex.batch.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learnspherex.batch.entity.Attendance;
import com.learnspherex.batch.entity.AttendanceStatus;
import com.learnspherex.batch.repository.AttendanceRepository;
import com.learnspherex.notification.event.NotificationEvent;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final ApplicationEventPublisher eventPublisher;

    private static final double LOW_ATTENDANCE_LIMIT = 75.0;


    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            ApplicationEventPublisher eventPublisher) {

        this.attendanceRepository = attendanceRepository;
        this.eventPublisher = eventPublisher;
    }


    // ==========================================
    // CREATE ATTENDANCE
    // ==========================================

    @Override
    public Attendance createAttendance(
            Attendance attendance) {

        Attendance savedAttendance =
                attendanceRepository.save(attendance);


        checkLowAttendance(
                attendance.getBatchId(),
                attendance.getStudentId());


        return savedAttendance;
    }


    // ==========================================
    // GET ALL ATTENDANCE
    // ==========================================

    @Override
    public List<Attendance> getAllAttendance() {

        return attendanceRepository.findAll();
    }


    // ==========================================
    // GET ATTENDANCE BY BATCH
    // ==========================================

    @Override
    public List<Attendance> getAttendanceByBatchId(
            Long batchId) {

        return attendanceRepository
                .findByBatchId(batchId);
    }


    // ==========================================
    // GET ATTENDANCE BY ID
    // ==========================================

    @Override
    public Attendance getAttendanceById(
            Long id) {

        return attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Attendance not found with id: "
                                        + id
                        )
                );
    }


    // ==========================================
    // UPDATE ATTENDANCE
    // ==========================================

    @Override
    public Attendance updateAttendance(
            Long id,
            Attendance attendance) {

        Attendance existingAttendance =
                getAttendanceById(id);


        existingAttendance.setBatchId(
                attendance.getBatchId());

        existingAttendance.setStudentId(
                attendance.getStudentId());

        existingAttendance.setAttendanceDate(
                attendance.getAttendanceDate());

        existingAttendance.setStatus(
                attendance.getStatus());


        Attendance updatedAttendance =
                attendanceRepository.save(
                        existingAttendance);


        checkLowAttendance(
                attendance.getBatchId(),
                attendance.getStudentId());


        return updatedAttendance;
    }


    // ==========================================
    // DELETE ATTENDANCE
    // ==========================================

    @Override
    public void deleteAttendance(Long id) {

        Attendance existingAttendance =
                getAttendanceById(id);

        attendanceRepository.delete(
                existingAttendance);
    }


    // ==========================================
    // LOW ATTENDANCE CALCULATION
    // ==========================================

    private void checkLowAttendance(
            Long batchId,
            Long studentId) {

        List<Attendance> records =
                attendanceRepository
                        .findByBatchIdAndStudentId(
                                batchId,
                                studentId);


        if (records.isEmpty()) {
            return;
        }


        int totalSessions =
                records.size();


        int attendedSessions = 0;


        for (Attendance record : records) {

            if (record.getStatus()
                    == AttendanceStatus.PRESENT

                    ||

                record.getStatus()
                    == AttendanceStatus.LATE) {

                attendedSessions++;
            }
        }


        double attendancePercentage =
                ((double) attendedSessions
                        / totalSessions)
                        * 100;


        // ======================================
        // LOW ATTENDANCE NOTIFICATION
        // ======================================

        if (attendancePercentage
                < LOW_ATTENDANCE_LIMIT) {

            eventPublisher.publishEvent(
                    new NotificationEvent(

                            studentId,

                            null,

                            "Low Attendance Alert",

                            "Your attendance for batch "
                                    + batchId
                                    + " is "
                                    + String.format(
                                            "%.2f",
                                            attendancePercentage)
                                    + "%. Please maintain "
                                    + "the required attendance.",

                            "LOW_ATTENDANCE"
                    )
            );
        }
    }
}