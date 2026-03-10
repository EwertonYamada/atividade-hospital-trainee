package com.hospital.ward.dto;

import com.hospital.room.dto.RoomRequest;
import com.hospital.ward.enums.Specialty;

public record WardRequest(
        Specialty specialty,
        RoomRequest roomRequest
) {}
