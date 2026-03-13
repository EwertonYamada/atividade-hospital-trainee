package com.hospital.exams.model;

import com.hospital.Doctor.model.Doctor;
import com.hospital.exams.enums.ExamName;
import com.hospital.exams.enums.ExamStatus;
import com.hospital.exams.enums.ExamType;
import com.hospital.patient.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "exam_name")
	private ExamName name;

	@Column(name = "exam_type")
	private ExamType type;

	@Column(name = "exam_status")
	private ExamStatus status;

	@Column(name = "exam_date_time")
	private LocalDateTime dateTime;


	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;


	public Exam(ExamName name, ExamType type, ExamStatus status, LocalDateTime dateTime, Patient patient, Doctor doctor) {
		this.name = name;
		this.type = type;
		this.status = status;
		this.dateTime = dateTime;
		this.patient = patient;
		this.doctor = doctor;
	}
}
