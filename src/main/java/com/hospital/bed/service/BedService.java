package com.hospital.bed.service;

import com.hospital.bed.dto.BedRequest;
import com.hospital.bed.enums.BedStatus;
import com.hospital.bed.model.Bed;
import com.hospital.bed.repository.BedRepository;
import com.hospital.room.model.Room;
import com.hospital.room.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    public List<Bed> create(Long roomId, @Valid BedRequest bedRequest) {
        Room room = this.roomService.getById(roomId);
        List<Bed> beds = this.buildBedService.buildBed(room, bedRequest.numberOfBeds(), bedRequest.bedType());
        return this.bedRepository.saveAll(beds);
    }

    public Bed getById(Long bedId) {
        return this.bedRepository.findById(bedId).orElseThrow(() ->
                new EntityNotFoundException("Bed with id " + bedId + " not found"));
    }

    public Bed getAvailableBedById(Long bedId) {
        return this.bedRepository.findAvailableBedById(bedId).orElseThrow(() -> new RuntimeException("Bed with id " + bedId + " is not available"));
    }

    public Bed save(Bed bed) {
        return this.bedRepository.save(bed);
    }

    public Bed updateBedStatus(Long bedId, BedStatus bedStatus) {
        Bed bed = this.getById(bedId);
        bed.setStatus(bedStatus);
        return this.save(bed);
    }
}
