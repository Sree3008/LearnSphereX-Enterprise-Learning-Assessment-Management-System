package com.learnspherex.batch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learnspherex.batch.entity.BatchSchedule;
import com.learnspherex.batch.repository.BatchScheduleRepository;

@Service
public class BatchScheduleServiceImpl implements BatchScheduleService {

    private final BatchScheduleRepository scheduleRepository;

    public BatchScheduleServiceImpl(
            BatchScheduleRepository scheduleRepository) {

        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public BatchSchedule createSchedule(BatchSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<BatchSchedule> getSchedulesByBatchId(Long batchId) {
        return scheduleRepository.findByBatchId(batchId);
    }

    @Override
    public List<BatchSchedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public BatchSchedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Schedule not found with id: " + id));
    }

    @Override
    public BatchSchedule updateSchedule(
            Long id,
            BatchSchedule schedule) {

        BatchSchedule existing =
                getScheduleById(id);

        existing.setBatchId(schedule.getBatchId());
        existing.setDayOfWeek(schedule.getDayOfWeek());
        existing.setStartTime(schedule.getStartTime());
        existing.setEndTime(schedule.getEndTime());

        return scheduleRepository.save(existing);
    }

    @Override
    public void deleteSchedule(Long id) {

        BatchSchedule existing =
                getScheduleById(id);

        scheduleRepository.delete(existing);
    }
}