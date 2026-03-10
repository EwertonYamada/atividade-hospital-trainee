package com.hospital.patient.dto;

import java.time.LocalDate;

public record PatientRequest(
        String name,
        String document,
        LocalDate birthDate
) {}
