package com.hospital.Doctor.service;

import com.hospital.Doctor.dto.DoctorRequest;
import com.hospital.Doctor.model.Doctor;
import com.hospital.Doctor.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor create(DoctorRequest doctorRequest) {
        Doctor newDoctor = new Doctor(doctorRequest);
        return this.save(newDoctor);
    }

    public Doctor save(Doctor newDoctor) {
        return this.doctorRepository.save(newDoctor);
    }

    public Doctor getDoctorToAdmission(Long doctorId) {
         return this.getById(doctorId);
    }

    public Doctor getById(Long doctorId) {
        return this.doctorRepository.findById(doctorId).orElseThrow(() ->
                new EntityNotFoundException("Doctor with id " + doctorId + " not found"));
    }
}
