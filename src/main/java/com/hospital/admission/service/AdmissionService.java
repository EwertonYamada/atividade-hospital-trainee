package com.hospital.admission.service;

import com.hospital.Doctor.model.Doctor;
import com.hospital.Doctor.service.DoctorService;
import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.bed.enums.BedStatus;
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
    private final DoctorService doctorService;

    public AdmissionService(
            AdmissionRepository admissionRepository,
            PatientService patientService,
            BedService bedService, DoctorService doctorService
    ) {
        this.admissionRepository = admissionRepository;
        this.patientService = patientService;
        this.bedService = bedService;
        this.doctorService = doctorService;
    }

    @Transactional
    public Admission admission(AdmissionRequest request) {
        Admission admission = this.prepareAdmission(request);
        this.admissionRepository.save(admission);
        this.updateBed(admission.getBed(), BedStatus.OCCUPIED);
        this.updatePatient(admission.getPatient(), Boolean.TRUE);
        return admission;
    }

    private void updateDoctor(Doctor doctor, Admission admission) {
        doctor.getAdmissions().add(admission);
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
        Doctor doctor = this.doctorService.getDoctorToAdmission(request.doctorId());
        if (!bed.getRoom().getWard().getSpecialty().equals(doctor.getSpecialty())) {
            throw new RuntimeException("THE SPECIALTY OF THE DOCTOR AND THE BED IS DIFFERENT");
        }

        return new Admission(bed, patient, doctor);
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

    public Admission putDoctor(Long admissionId, Long doctorId) {
        Admission admission = this.getById(admissionId);
        Doctor doctor = this.doctorService.getDoctorToAdmission(doctorId);
        this.validateAdmissionBeforePut(admission, doctor);
        admission.getDoctors().add(doctor);
        this.updateDoctor(doctor, admission);
        this.admissionRepository.save(admission);
        return admission;
    }

    private void validateAdmissionBeforePut(Admission admission, Doctor doctor) {
        if (Objects.nonNull(admission.getDischargedAt()) || AdmissionStatus.INACTIVE.equals(admission.getStatus())) {
            throw new RuntimeException("The admission with id " + admission.getId() + "cannot be updated because is discharged");
        }
        if (admission.getDoctors().contains(doctor)) {
            throw new RuntimeException("the doctor with id " + doctor.getId() + "is already in the admission");
        }
    }

    public Admission removeDoctor(Long admissionId, Long doctorId) {
        Admission admission = this.getById(admissionId);
        Doctor doctor = this.doctorService.getDoctorToAdmission(doctorId);
        this.validateAdmissionBeforeRemove(admission,doctor);
        admission.getDoctors().remove(doctor);
        doctor.getAdmissions().remove(admission);
        return admission;
    }

    private void validateAdmissionBeforeRemove(Admission admission, Doctor doctor) {
        if (Objects.nonNull(admission.getDischargedAt()) || AdmissionStatus.INACTIVE.equals(admission.getStatus())) {
            throw new RuntimeException("The admission with id " + admission.getId() + "cannot be updated because is discharged");
        }
        if (!admission.getDoctors().contains(doctor)) {
            throw new RuntimeException("the doctor with id " + doctor.getId() + "is not in the admission");
        }
    }
}