package com.hospital.admission.dto;

import java.util.List;

public record AdmissionRequest(
        Long bedId,
        Long patientId,
        Long doctorId
) {
}
