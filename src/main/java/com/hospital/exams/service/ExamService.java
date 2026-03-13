package com.hospital.exams.service;

import com.hospital.Doctor.model.Doctor;
import com.hospital.Doctor.repository.repo;
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
	private final repo repo;


	public ExamService(ExamRepository examRepository, PatientService patientService, repo repo) {
		this.examRepository = examRepository;
		this.patientService = patientService;
		this.repo = repo;
	}

	public Exam create(ExamRequest examRequest){
		Patient patient = this.patientService.getPatientToDischarge(examRequest.getPatient_id());
		Doctor doctor = repo.save(new Doctor());
		examRequest.setDate_time(LocalDateTime.now().plusHours(1));
		this.check_time(examRequest.getDate_time(),examRequest.getPatient_id());

		Exam exam = new Exam(examRequest.getExam_name(),examRequest.getExam_type(), ExamStatus.SCHEDULED,examRequest.getDate_time(),patient,doctor);

		return this.examRepository.save(exam);
	}



	private void check_time(LocalDateTime dateTime,Long patient_id){

		if (dateTime.isBefore(LocalDateTime.now())){
			throw new RuntimeException("Data e Horario Invalidos");
		}

		if (!this.examRepository.check_time(patient_id,dateTime.minusHours(1),dateTime.plusHours(1)).isEmpty()){
			throw new RuntimeException("Intervalo Entre Agendamentos Invalido");
		}

	}


	public List<Exam>getAll(){return this.examRepository.findAll();}

	public Exam getById(Long id){
		return this.examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exame Nao Encontrado"));
	}



	public Exam changeStatus(Long id, ExamStatus status){
		Exam exam = getById(id);
		exam.setStatus(status);

		return this.examRepository.save(exam);
	}



}



