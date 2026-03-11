package com.hospital.patient.service;

import com.hospital.patient.dto.PatientRequest;
import com.hospital.patient.model.Patient;
import com.hospital.patient.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient create(PatientRequest patientRequest) {
        Patient patient = new Patient(patientRequest);
        return this.save(patient);
    }

    public Patient getById(Long id) {
        return this.patientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Patient with id " + id + " not found"));
    }

    public Patient getPatientToAdmission(Long patientId) {
        Patient patient = this.getById(patientId);
        if (patient.isHospitalized()) throw new RuntimeException("Patient already is hospitalized");
        return patient;
    }

    public Patient getPatientToDischarge(Long patientId) {
        Patient patient = this.getById(patientId);
        if (!patient.isHospitalized()) throw new RuntimeException("Patient is note hospitalized");
        return patient;
    }

    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }

}
