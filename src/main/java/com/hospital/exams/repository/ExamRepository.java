package com.hospital.exams.repository;

import com.hospital.exams.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,Long> {

 @Query("select e " +
		 "from Exam e " +
		 "where e.patient.id = :patient_id  " +
		 "and e.dateTime > :anterior " +
		 "and e.dateTime < :depois ")
	public List<Exam> check_time(Long patient_id,LocalDateTime anterior, LocalDateTime depois);



}
