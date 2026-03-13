package com.hospital.admission.service;

import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.bed.enums.BedStatus;
import com.hospital.bed.enums.BedType;
import com.hospital.bed.model.Bed;
import com.hospital.bed.service.BedService;
import com.hospital.admission.dto.AdmissionRequest;
import com.hospital.admission.model.Admission;
import com.hospital.admission.repository.AdmissionRepository;
import com.hospital.patient.model.Patient;
import com.hospital.patient.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PatientService patientService;
    private final BedService bedService;

    public AdmissionService(
            AdmissionRepository admissionRepository,
            PatientService patientService,
            BedService bedService
    ) {
        this.admissionRepository = admissionRepository;
        this.patientService = patientService;
        this.bedService = bedService;
    }

    @Transactional
    public Admission admission(AdmissionRequest request) {
        Admission admission = this.prepareAdmission(request);
        this.admissionRepository.save(admission);
        this.updateBed(admission.getBed(), BedStatus.OCCUPIED);
        this.updatePatient(admission.getPatient(), Boolean.TRUE);
        return admission;
    }

    private void updatePatient(Patient patient, Boolean isHospitalized) {
        patient.setHospitalized(isHospitalized);
        this.patientService.save(patient);
    }

    private void updateBed(Bed bed, BedStatus bedStatus) {
        bed.setStatus(bedStatus);
        this.bedService.save(bed);
    }

    private Admission prepareAdmission(AdmissionRequest request) {
        Patient patient = this.patientService.getPatientToAdmission(request.patientId());
        Bed bed = this.bedService.getAvailableBedById(request.bedId());
        return new Admission(bed, patient);
    }

    private Admission getById(Long admissionId) {
        return this.admissionRepository.findById(admissionId).orElseThrow(() ->
                new EntityNotFoundException("Admission with id " + admissionId + " not found"));
    }


    public Admission discharge(Long admissionId) {
        Admission admission = this.getById(admissionId);
        this.validateAdmissionBeforeDischarge(admission);
        admission.setDischargedAt(new Date());
        admission.setStatus(AdmissionStatus.INACTIVE);
        this.admissionRepository.save(admission);
        this.updateBed(admission.getBed(), BedStatus.IN_PREPARATION);
        this.updatePatient(admission.getPatient(), Boolean.FALSE);
        return admission;
    }

    private void validateAdmissionBeforeDischarge(Admission admission) {
        if (Objects.nonNull(admission.getDischargedAt()) || AdmissionStatus.INACTIVE.equals(admission.getStatus()))
            throw new RuntimeException("The patient with id " +admission.getPatient().getId() + " has already been discharged.");
    }

    public Admission transfer(AdmissionRequest request) {

        Admission pastAdmission = this.admissionRepository.findActiveAdmission(request.patientId());
        discharge(pastAdmission.getId());

        Admission newAdmission = prepareAdmission(request);
        this.admissionRepository.save(newAdmission);
        this.updateBed(newAdmission.getBed(), BedStatus.OCCUPIED);
        this.updatePatient(newAdmission.getPatient(), Boolean.TRUE);
        return newAdmission;

    }
}
