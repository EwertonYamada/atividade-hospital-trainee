package com.hospital.visit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VisitRequest(
        @NotBlank(message = "O nome do visitante é obrigatório")
        String visitorName,

        @NotBlank(message = "O documento do visitante é obrigatório")
        String visitorDocument,

        @NotNull(message = "O ID do paciente é obrigatório")
        Long patientId
) {}