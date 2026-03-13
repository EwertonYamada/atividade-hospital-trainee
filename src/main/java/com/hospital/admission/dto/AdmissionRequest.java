package com.hospital.admission.dto;

import com.hospital.bed.enums.BedType;

public record AdmissionRequest(
        Long bedId,
        Long patientId,
        BedType bedType
) {
}
