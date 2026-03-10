package com.hospital.ward.service;

import com.hospital.hospital.model.Hospital;
import com.hospital.hospital.service.HospitalService;
import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.model.Ward;
import com.hospital.ward.repository.WardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WardService {

    private final HospitalService hospitalService;
    private final BuildWardService buildWardService;
    private final WardRepository wardRepository;

    public WardService(HospitalService hospitalService, BuildWardService buildWardService, WardRepository wardRepository) {
        this.hospitalService = hospitalService;
        this.buildWardService = buildWardService;
        this.wardRepository = wardRepository;
    }

    @Transactional
    public List<Ward> create(Long hospitalId, List<WardRequest> wardRequests) {
        if (wardRequests.isEmpty()) throw new IllegalArgumentException("Ward list not provided");
        Hospital hospital = this.hospitalService.getById(hospitalId);
        List<Ward> wards = this.buildWardService.buildWards(hospital, wardRequests);
        return this.wardRepository.saveAll(wards);
    }

    public Ward getById(Long wardId) {
        return this.wardRepository.findById(wardId).orElseThrow(() -> new EntityNotFoundException("Ward with id " + wardId + " not found"));
    }
}
