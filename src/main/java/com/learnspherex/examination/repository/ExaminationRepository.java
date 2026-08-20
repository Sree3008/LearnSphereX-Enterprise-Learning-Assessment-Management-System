package com.learnspherex.examination.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnspherex.examination.entity.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

}