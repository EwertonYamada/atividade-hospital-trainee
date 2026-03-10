package com.hospital.bed.service;

import com.hospital.bed.enums.BedType;
import com.hospital.bed.model.Bed;
import com.hospital.bed.repository.BedRepository;
import com.hospital.room.model.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildBedService {

    private final BedRepository bedRepository;

    public BuildBedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    public List<Bed> buildBed(Room room, Integer numberOfBedsPerRoom) {
        List<Bed> beds = new ArrayList<>();
        Integer lastBedNumber = this.bedRepository.findLastBedNumber();
        for (int i = 0; i < numberOfBedsPerRoom; i++) {
            Integer nextBedNumber = lastBedNumber + i + 1;
            Bed bed = new Bed(room, nextBedNumber, Boolean.TRUE, BedType.INFIRMARY);
            beds.add(bed);
        }
        return beds;
    }
}
