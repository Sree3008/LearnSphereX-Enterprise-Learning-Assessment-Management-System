package com.learnspherex.batch.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learnspherex.batch.entity.Batch;
import com.learnspherex.batch.repository.BatchRepository;
import com.learnspherex.notification.event.NotificationEvent;

@Service
public class BatchServiceImpl implements BatchService {

    private final BatchRepository batchRepository;

    private final ApplicationEventPublisher eventPublisher;


    public BatchServiceImpl(
            BatchRepository batchRepository,
            ApplicationEventPublisher eventPublisher) {

        this.batchRepository = batchRepository;
        this.eventPublisher = eventPublisher;
    }


    // ==========================================
    // CREATE BATCH
    // ==========================================

    @Override
    public Batch createBatch(Batch batch) {

        Batch savedBatch =
                batchRepository.save(batch);


        // Create notification event

        eventPublisher.publishEvent(
                new NotificationEvent(
                        null,
                        null,
                        "New Batch Announcement",
                        "A new batch "
                                + savedBatch.getBatchName()
                                + " has been created.",
                        "BATCH_ANNOUNCEMENT"
                )
        );


        return savedBatch;
    }


    // ==========================================
    // GET ALL BATCHES
    // ==========================================

    @Override
    public List<Batch> getAllBatches() {

        return batchRepository.findAll();
    }


    // ==========================================
    // GET BATCH BY ID
    // ==========================================

    @Override
    public Batch getBatchById(Long id) {

        return batchRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Batch not found with id: "
                                        + id
                        )
                );
    }


    // ==========================================
    // UPDATE BATCH
    // ==========================================

    @Override
    public Batch updateBatch(
            Long id,
            Batch batch) {

        Batch existingBatch =
                getBatchById(id);


        existingBatch.setBatchName(
                batch.getBatchName());

        existingBatch.setBatchMode(
                batch.getBatchMode());

        existingBatch.setBatchStatus(
                batch.getBatchStatus());

        existingBatch.setCapacity(
                batch.getCapacity());

        existingBatch.setCourseId(
                batch.getCourseId());

        existingBatch.setStartDate(
                batch.getStartDate());

        existingBatch.setEndDate(
                batch.getEndDate());

        existingBatch.setTrainerId(
                batch.getTrainerId());


        return batchRepository.save(
                existingBatch);
    }


    // ==========================================
    // DELETE BATCH
    // ==========================================

    @Override
    public void deleteBatch(Long id) {

        Batch existingBatch =
                getBatchById(id);

        batchRepository.delete(
                existingBatch);
    }
}