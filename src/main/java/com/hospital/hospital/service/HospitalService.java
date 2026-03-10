package com.hospital.hospital.service;

import com.hospital.hospital.dto.HospitalRequest;
import com.hospital.hospital.model.Hospital;
import com.hospital.hospital.repository.HospitalRepository;
import com.hospital.utils.exceptions.AlreadyExistingEntityException;
import com.hospital.ward.service.BuildWardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final BuildWardService buildWardService;

    public HospitalService(HospitalRepository hospitalRepository, BuildWardService buildWardService) {
        this.hospitalRepository = hospitalRepository;
        this.buildWardService = buildWardService;
    }

    @Transactional
    public Hospital create(HospitalRequest hospitalRequest) {
        this.validateExistingHospital(hospitalRequest.cnpj());
        Hospital hospital = new Hospital(hospitalRequest.name(), hospitalRequest.phoneNumber(), hospitalRequest.cnpj());
        this.buildWardService.buildWards(hospital, hospitalRequest.specialties());
        return this.hospitalRepository.save(hospital);
    }

    public void validateExistingHospital(String cnpj) {
        if (this.existingHospital(cnpj))
            throw new AlreadyExistingEntityException("Hospital with CNPJ " + cnpj + " already exists.");
    }

    private Boolean existingHospital(String cnpj) {
        return this.hospitalRepository.existsHospitalByCnpj(cnpj);
    }

    @Transactional(readOnly = true)
    public Hospital getById(Long id) {
        return this.hospitalRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Hospital with id " + id + "not found: "));
    }

    @Transactional
    public Hospital update(Long id, HospitalRequest hospitalRequest) {
        this.validateExistingHospital(hospitalRequest.cnpj());
        Hospital hospital = this.getById(id);
        hospital.setCnpj(hospitalRequest.cnpj());
        hospital.setName(hospitalRequest.name());
        hospital.setPhoneNumber(hospitalRequest.phoneNumber());
        return this.hospitalRepository.save(hospital);
    }

    public void deleteById(Long id) {
        this.hospitalRepository.deleteById(id);
    }
}
