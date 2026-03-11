package com.hospital.ward.service;

import com.hospital.hospital.model.Hospital;
import com.hospital.room.model.Room;
import com.hospital.room.service.BuildRoomService;
import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.model.Ward;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildWardService {

    private final BuildRoomService buildRoomService;

    public BuildWardService(BuildRoomService buildRoomService) {
        this.buildRoomService = buildRoomService;
    }

    public List<Ward> buildWards(Hospital hospital, List<WardRequest> specialties) {
        List<Ward> alreadyRegisteredWardList = hospital.getWards();
        List<WardRequest> wardsRequestToAdd = specialties.stream()
                .filter(specialty -> alreadyRegisteredWardList.stream()
                        .noneMatch(registeredWard -> registeredWard.getSpecialty().equals(specialty.specialty())))
                .toList();

        List<Ward> newWardsToAdd = new ArrayList<>();

        wardsRequestToAdd.forEach(specialty -> {
            Ward ward = new Ward(specialty.specialty(), hospital);
            List<Room> rooms = buildRoomService.buildRoom(ward, specialty.roomRequest());
            ward.setRooms(rooms);
            newWardsToAdd.add(ward);
        });
        hospital.getWards().addAll(newWardsToAdd);
        return newWardsToAdd;
    }
}
