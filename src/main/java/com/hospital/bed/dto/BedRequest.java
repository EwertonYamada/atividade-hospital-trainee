package com.hospital.bed.dto;

import com.hospital.bed.enums.BedType;

public record BedRequest(
        Integer numberOfBeds,
        BedType bedType
) {}
