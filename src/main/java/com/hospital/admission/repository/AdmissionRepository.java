package com.hospital.admission.repository;

import com.hospital.Doctor.model.Doctor;
import com.hospital.admission.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {


	@Query("SELECT a.doctors from Admission a where a.patient.id = :patient_id ")
	public List<Doctor> doctorsPerPatient(Long patient_id);


}
