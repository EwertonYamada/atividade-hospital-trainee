package com.hospital.room.service;

import com.hospital.bed.model.Bed;
import com.hospital.bed.service.BedService;
import com.hospital.room.model.Room;
import com.hospital.room.repository.RoomRepository;
import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.model.Ward;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final BedService bedService;

    public RoomService(RoomRepository roomRepository, BedService bedService) {
        this.roomRepository = roomRepository;
        this.bedService = bedService;
    }

    public List<Room> buildRoom(Ward ward, WardRequest specialty) {
        String specialtyPrefix = this.getSpecialtyPrefix(specialty);
        List<Room> rooms = new ArrayList<>();
        Integer lastRegisteredRoomNumber = this.roomRepository.findLastRoomNumber();
        for (int i = 0; i < specialty.numberOfRooms(); i++) {
            Integer nextRoomNumber = lastRegisteredRoomNumber + i + 1;
            String code = specialtyPrefix.concat(String.valueOf(nextRoomNumber));
            Room room = new Room(code, ward);
            List<Bed> beds = this.bedService.buildBed(room, specialty);
            room.setBeds(beds);
            rooms.add(room);
        }
        return rooms;
    }

    private String getSpecialtyPrefix(WardRequest specialty) {
        return specialty.specialty().toString().substring(0, 4).concat("-");
    }
}
