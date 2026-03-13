package com.hospital.exams.service;


import com.hospital.Doctor.model.Doctor;
import com.hospital.Doctor.service.DoctorService;
import com.hospital.admission.repository.AdmissionRepository;
import com.hospital.exams.dto.ExamRequest;
import com.hospital.exams.enums.ExamStatus;
import com.hospital.exams.model.Exam;
import com.hospital.exams.repository.ExamRepository;
import com.hospital.patient.model.Patient;
import com.hospital.patient.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {

	private final ExamRepository examRepository;
	private final PatientService patientService;
	private final DoctorService doctorService;
	private final AdmissionRepository admissionRepository;


	public ExamService(ExamRepository examRepository, PatientService patientService, DoctorService doctorService, AdmissionRepository admissionRepository) {
		this.examRepository = examRepository;
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.admissionRepository = admissionRepository;
	}

	public Exam create(ExamRequest examRequest) {
		Patient patient = this.patientService.getPatientToDischarge(examRequest.getPatientId());

		Doctor doctor = doctorService.getById(examRequest.getDoctorId());
		check_doctor(examRequest.getPatientId(), doctor);

		this.check_time(examRequest.getDateTime(), examRequest.getPatientId());

		Exam exam = new Exam(examRequest.getExamName(), examRequest.getExamType(), ExamStatus.SCHEDULED, examRequest.getDateTime(), patient, doctor);

		return this.examRepository.save(exam);
	}


	private void check_time(LocalDateTime dateTime, Long patient_id) {

		if (dateTime.isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Data e Horario Invalidos");
		}

		if (!this.examRepository.check_time(patient_id, dateTime.minusHours(1), dateTime.plusHours(1)).isEmpty()) {
			throw new RuntimeException("Intervalo Entre Agendamentos Invalido");
		}

	}

	private void check_doctor(Long patient_id, Doctor doctor) {
		if (!this.admissionRepository.doctorsPerPatient(patient_id).contains(doctor)) {
			throw new RuntimeException("Medico nao responsavel por este paciente");
		}
	}


	public List<Exam> getAll() {
		return this.examRepository.findAll();
	}

	public Exam getById(Long id) {
		return this.examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exame Nao Encontrado"));
	}


	public Exam changeStatus(Long id, ExamStatus status) {
		Exam exam = getById(id);
		exam.setStatus(status);

		return this.examRepository.save(exam);
	}


}



