package com.hospital.exams.dto;

import com.hospital.exams.enums.ExamName;
import com.hospital.exams.enums.ExamType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamRequest {

	private Long patient_id;
	private Long doctor_id = 0L;

	@Enumerated(EnumType.STRING)
	private ExamName exam_name;

	@Enumerated(EnumType.STRING)
	private ExamType exam_type;

	private LocalDateTime date_time;

}
