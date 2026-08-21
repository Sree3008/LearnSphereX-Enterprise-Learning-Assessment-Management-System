package com.learnspherex.batch.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.learnspherex.batch.entity.BatchSession;
import com.learnspherex.batch.repository.BatchSessionRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class BatchSessionServiceImpl implements BatchSessionService {

    private final BatchSessionRepository sessionRepository;

    public BatchSessionServiceImpl(BatchSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public BatchSession createSession(BatchSession session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<BatchSession> getSessionsByBatchId(Long batchId) {
        return sessionRepository.findByBatchId(batchId);
    }

    @Override
    public BatchSession getSessionById(Long id) {

        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Session not found with id: " + id));
    }

    @Override
    public BatchSession updateSession(Long id, BatchSession session) {

        BatchSession existing = getSessionById(id);

        existing.setBatchId(session.getBatchId());
        existing.setSessionDate(session.getSessionDate());
        existing.setStartTime(session.getStartTime());
        existing.setEndTime(session.getEndTime());
        existing.setTopic(session.getTopic());
        existing.setStatus(session.getStatus());

        return sessionRepository.save(existing);
    }

    @Override
    public void deleteSession(Long id) {
        BatchSession existing = getSessionById(id);
        sessionRepository.delete(existing);
    }
}