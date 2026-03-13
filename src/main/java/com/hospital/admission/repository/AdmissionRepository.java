package com.hospital.admission.repository;

import com.hospital.admission.model.Admission;
import com.hospital.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    Admission findAdmissionByPatientAndStatus_Active(Patient patient);
}
