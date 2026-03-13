package com.hospital.bed.controller;

import com.hospital.bed.dto.BedRequest;
import com.hospital.bed.enums.BedStatus;
import com.hospital.bed.model.Bed;
import com.hospital.bed.service.BedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bed")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<List<Bed>> create(@PathVariable Long roomId, @RequestBody BedRequest bedRequest) {
        return ResponseEntity.ok(this.bedService.create(roomId, bedRequest));
    }

    @PatchMapping("/{id}/update-bed-status")
    public ResponseEntity<Bed> updateBedStatus(@PathVariable Long id, @RequestParam BedStatus bedStatus) {
        return ResponseEntity.ok(this.bedService.updateBedStatus(id, bedStatus));
    }
}
