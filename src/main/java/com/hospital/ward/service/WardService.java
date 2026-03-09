package com.hospital.ward.service;

import com.hospital.hospital.model.Hospital;
import com.hospital.room.model.Room;
import com.hospital.room.service.RoomService;
import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.model.Ward;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WardService {

    private final RoomService roomService;

    public WardService(RoomService roomService) {
        this.roomService = roomService;
    }

    public List<Ward> buildWards(Hospital hospital, List<WardRequest> specialties) {
        List<Ward> wards = new ArrayList<>();
        specialties.forEach(specialty -> {
            Ward ward = new Ward(specialty.specialty(), hospital);
            List<Room> rooms = this.roomService.buildRoom(ward, specialty);
            ward.setRooms(rooms);
            wards.add(ward);
        });
        return wards;
    }
}
