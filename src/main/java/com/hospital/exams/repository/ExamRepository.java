package com.hospital.exams.repository;

import com.hospital.exams.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

	@Query("select e " +
			"from Exam e " +
			"where e.patient.id = :patientId  " +
			"and e.dateTime > :before " +
			"and e.dateTime < :after ")
	public List<Exam> check_time(Long patientId, LocalDateTime before, LocalDateTime after);

}
