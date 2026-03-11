package com.hospital.admission.dto;

public record AdmissionRequest(
        Long bedId,
        Long patientId
) {
}
