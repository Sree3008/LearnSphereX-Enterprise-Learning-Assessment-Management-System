package com.learnspherex.batch.entity;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

import jakarta.persistence.*;

@Entity
@Table(name="batches")
public class Batch {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message="Batch name is required")
	private String batchName;
	@Enumerated(EnumType.STRING)
	private BatchMode batchMode;
	@Enumerated(EnumType.STRING)
	private BatchStatus batchStatus;
	
	private LocalDate startDate;
	private LocalDate endDate;
	@NotNull(message = "Capacity is required")
	@Positive(message = "Capacity must be greater than 0")
	private Integer capacity;
	private Long trainerId;
	private Long courseId;
	public Batch() {
		
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public BatchMode getBatchMode() {
		return batchMode;
	}
	public void setBatchMode(BatchMode batchMode) {
		this.batchMode = batchMode;
	}
	public BatchStatus getBatchStatus() {
		return batchStatus;
	}
	public void setBatchStatus(BatchStatus batchStatus) {
		this.batchStatus = batchStatus;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Long getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	
	
	
}
