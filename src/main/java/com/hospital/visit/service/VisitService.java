package com.hospital.visit.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.admission.repository.AdmissionRepository;
import com.hospital.patient.model.Patient;
import com.hospital.patient.repository.PatientRepository;
import com.hospital.visit.dto.VisitRequest;
import com.hospital.visit.model.Visit;
import com.hospital.visit.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class VisitService {

    @Autowired private VisitRepository visitRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired private AdmissionRepository admissionRepository;



    public Visit startVisit(VisitRequest request) {
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        // Só é possível registrar visita a paciente com internação ativa
        boolean isHospitalized = admissionRepository.existsByPatientIdAndStatus(
                patient.getId(),
                AdmissionStatus.ACTIVE
        );

        if (!isHospitalized) {
            throw new RuntimeException("O paciente não possui uma internação ativa.");
        }

        // Um paciente pode receber várias visitas no mesmo dia, porém um visitante por vez
        boolean hasVisitor = visitRepository.existsByPatientIdAndExitDateTimeIsNull(patient.getId());
        if (hasVisitor) {
            throw new RuntimeException("O paciente já está com visita");
        }


        Visit visit = new Visit();
        visit.setVisitorName(request.visitorName());
        visit.setVisitorDocument(request.visitorDocument());
        visit.setEntryDateTime(LocalDateTime.now()); // Registrar visita com a data corrente (now)
        visit.setPatient(patient);

        return visitRepository.save(visit);
    }


    public Visit endVisit(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visita não encontrada."));

        if (visit.getExitDateTime() != null) {
            throw new RuntimeException("A saída deste visitante já foi registrada.");
        }

        visit.setExitDateTime(LocalDateTime.now());
        return visitRepository.save(visit);
    }
}