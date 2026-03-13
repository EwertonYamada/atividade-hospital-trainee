package com.hospital.room.service;

import com.hospital.bed.model.Bed;
import com.hospital.bed.service.BuildBedService;
import com.hospital.room.dto.RoomRequest;
import com.hospital.room.model.Room;
import com.hospital.room.repository.RoomRepository;
import com.hospital.ward.enums.Specialty;
import com.hospital.ward.model.Ward;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BuildRoomService {

    private final RoomRepository roomRepository;
    private final BuildBedService buildBedService;

    public BuildRoomService(RoomRepository roomRepository, BuildBedService buildBedService) {
        this.roomRepository = roomRepository;
        this.buildBedService = buildBedService;
    }

    public List<Room> buildRoom(Ward ward, RoomRequest roomRequest) {
        List<Room> rooms = new ArrayList<>();
        if (Objects.nonNull(roomRequest) && roomRequest.numberOfRooms() >= 1) {
            String specialtyPrefix = this.getSpecialtyPrefix(ward.getSpecialty());
            Integer lastRegisteredRoomNumber = this.roomRepository.findLastRoomNumber(ward.getSpecialty().toString());
            for (int i = 0; i < roomRequest.numberOfRooms(); i++) {
                Integer nextRoomNumber = lastRegisteredRoomNumber + i + 1;
                String code = specialtyPrefix.concat(String.valueOf(nextRoomNumber));
                Room room = new Room(code, ward);
                List<Bed> beds = this.buildBedService.buildBed(room, roomRequest.bedRequest().numberOfBeds(), roomRequest.bedRequest().bedType());
                room.setBeds(beds);
                rooms.add(room);
            }
        }
        return rooms;
    }

    private String getSpecialtyPrefix(Specialty specialty) {
        return specialty.toString().substring(0, 4).concat("-");
    }
}
