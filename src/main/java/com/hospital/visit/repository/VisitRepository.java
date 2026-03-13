package com.hospital.visit.repository;

import com.hospital.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    // Verifica se ja ta ou n com visita o paciente
    boolean existsByPatientIdAndExitDateTimeIsNull(Long patientId);
}