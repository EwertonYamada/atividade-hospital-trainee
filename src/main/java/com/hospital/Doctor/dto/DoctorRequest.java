package com.hospital.Doctor.dto;

import com.hospital.ward.enums.Specialty;

public record DoctorRequest(
        String name,
        String crm,
        Specialty specialty
) {
}
