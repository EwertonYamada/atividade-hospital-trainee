package com.hospital.ward.dto;

import com.hospital.ward.enums.Specialty;

public record WardRequest(
        Specialty specialty,
        Integer numberOfRooms,
        Integer numberOfBedsPerRoom
) {}
