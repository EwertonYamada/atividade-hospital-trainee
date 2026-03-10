package com.hospital.bed.service;

import com.hospital.bed.dto.BedRequest;
import com.hospital.bed.model.Bed;
import com.hospital.bed.repository.BedRepository;
import com.hospital.room.model.Room;
import com.hospital.room.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedService {

    private final BedRepository bedRepository;
    private final RoomService roomService;
    private final BuildBedService buildBedService;

    public BedService(BedRepository bedRepository, RoomService roomService, BuildBedService buildBedService) {
        this.bedRepository = bedRepository;
        this.roomService = roomService;
        this.buildBedService = buildBedService;
    }

    public List<Bed> create(Long roomId, BedRequest bedRequest) {
        Room room = this.roomService.getById(roomId);
        List<Bed> beds = this.buildBedService.buildBed(room, bedRequest.numberOfBeds());
        return this.bedRepository.saveAll(beds);
    }
}
