package com.hospital.room.dto;

import com.hospital.bed.dto.BedRequest;

public record RoomRequest(
        Integer numberOfRooms,
        BedRequest bedRequest
) {}
