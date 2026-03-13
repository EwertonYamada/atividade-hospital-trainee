package com.hospital.admission.repository;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    boolean existsByPatientIdAndStatus(Long patient_id, AdmissionStatus status);
}
