package com.hospital.room.service;

import com.hospital.room.dto.RoomRequest;
import com.hospital.room.model.Room;
import com.hospital.room.repository.RoomRepository;
import com.hospital.ward.model.Ward;
import com.hospital.ward.service.WardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final WardService wardService;
    private final BuildRoomService buildRoomService;

    public RoomService(RoomRepository roomRepository, WardService wardService, BuildRoomService buildRoomService) {
        this.roomRepository = roomRepository;
        this.wardService = wardService;
        this.buildRoomService = buildRoomService;
    }

    public List<Room> create(Long wardId, RoomRequest roomRequest) {
        Ward ward = this.wardService.getById(wardId);
        List<Room> rooms = this.buildRoomService.buildRoom(ward, roomRequest.numberOfRooms(), roomRequest.bedRequest().numberOfBeds());
        return this.roomRepository.saveAll(rooms);
    }

    public Room getById(Long roomId) {
        return this.roomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException("Room with " + roomId + " not found"));
    }
}
