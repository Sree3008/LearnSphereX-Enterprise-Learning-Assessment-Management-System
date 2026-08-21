package com.learnspherex.batch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.learnspherex.batch.entity.Attendance;
import com.learnspherex.batch.service.AttendanceService;

@Controller
public class AttendancePageController {

    private final AttendanceService attendanceService;

    public AttendancePageController(
            AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendance")
    public String showAllAttendance(Model model) {

        model.addAttribute(
                "attendances",
                attendanceService.getAllAttendance());

        return "attendances";
    }

    @GetMapping("/attendance/batch/{batchId}")
    public String showAttendanceByBatch(
            @PathVariable Long batchId,
            Model model) {

        model.addAttribute(
                "attendances",
                attendanceService
                        .getAttendanceByBatchId(batchId));

        model.addAttribute("batchId", batchId);

        return "attendances";
    }

    @GetMapping("/attendance/new")
    public String showCreateForm(Model model) {

        model.addAttribute(
                "attendance",
                new Attendance());

        return "attendance-form";
    }

    @PostMapping("/attendance")
    public String createAttendance(
            @ModelAttribute Attendance attendance) {

        attendanceService.createAttendance(attendance);

        return "redirect:/attendance";
    }

    @GetMapping("/attendance/{id}")
    public String showAttendanceDetails(
            @PathVariable Long id,
            Model model) {

        Attendance attendance =
                attendanceService.getAttendanceById(id);

        model.addAttribute(
                "attendance",
                attendance);

        return "attendance-details";
    }

    @GetMapping("/attendance/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Attendance attendance =
                attendanceService.getAttendanceById(id);

        model.addAttribute(
                "attendance",
                attendance);

        return "attendance-form";
    }

    @PostMapping("/attendance/{id}")
    public String updateAttendance(
            @PathVariable Long id,
            @ModelAttribute Attendance attendance) {

        attendanceService.updateAttendance(
                id, attendance);

        return "redirect:/attendance";
    }

    @PostMapping("/attendance/{id}/delete")
    public String deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return "redirect:/attendance";
    }
}