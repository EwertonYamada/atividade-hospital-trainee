package com.hospital.admission.repository;

import com.hospital.admission.model.Admission;
import com.hospital.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    @Query("select a from Admission a " +
            "where a.patient.id = :patientId " +
            "and a.status = 'ACTIVE' ")
    Admission findActiveAdmission(@Param("patientId") Long patientId);
}
