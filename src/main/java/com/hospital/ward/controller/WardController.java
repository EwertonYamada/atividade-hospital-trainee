package com.hospital.ward.controller;

import com.hospital.ward.dto.WardRequest;
import com.hospital.ward.model.Ward;
import com.hospital.ward.service.WardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ward")
public class WardController {

    private final WardService wardService;

    public WardController(WardService wardService) {
        this.wardService = wardService;
    }

    @PostMapping("hospital/{hospitalId}")
    public ResponseEntity<List<Ward>> create(@PathVariable Long hospitalId, @RequestBody List<WardRequest> wardRequests) {
        return ResponseEntity.ok(this.wardService.create(hospitalId, wardRequests));
    }
}
