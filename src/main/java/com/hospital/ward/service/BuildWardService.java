package com.hospital.ward.service;

import com.hospital.hospital.model.Hospital;
import com.hospital.room.model.Room;
import com.hospital.room.service.BuildRoomService;
import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.enums.Specialty;
import com.hospital.ward.model.Ward;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildWardService {

    private final BuildRoomService buildRoomService;

    public BuildWardService(BuildRoomService buildRoomService) {
        this.buildRoomService = buildRoomService;
    }

    public List<Ward> buildWards(Hospital hospital, List<WardRequest> specialties) {
        List<Ward> wards = hospital.getWards();
        specialties.forEach(specialty -> {
            Specialty specialtyEnum = specialty.specialty();
            Ward registeredWard = wards.stream()
                    .filter(ward -> ward.getSpecialty().equals(specialtyEnum))
                    .findFirst()
                    .orElse(null);

            Ward ward;

            if (registeredWard != null) {
                ward = registeredWard;
            } else {
                ward = new Ward(specialtyEnum, hospital);
                wards.add(ward);
            }

            List<Room> rooms = buildRoomService.buildRoom(
                    ward,
                    specialty.roomRequest().numberOfRooms(),
                    specialty.roomRequest().bedRequest().numberOfBeds()
            );
            ward.setRooms(rooms);
        });
        return wards;
    }
}
