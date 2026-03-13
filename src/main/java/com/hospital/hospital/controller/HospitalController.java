package com.hospital.hospital.controller;

import com.hospital.hospital.dto.HospitalRequest;
import com.hospital.hospital.model.Hospital;
import com.hospital.hospital.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping
    public ResponseEntity<Hospital> create( @RequestBody HospitalRequest hospital) {
        return ResponseEntity.ok(this.hospitalService.create(hospital));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.hospitalService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> update(@PathVariable Long id, @RequestBody HospitalRequest hospital) {
        return ResponseEntity.ok(this.hospitalService.update(id, hospital));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.hospitalService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
