package com.hospital.Doctor.controller;

import com.hospital.Doctor.dto.DoctorRequest;
import com.hospital.Doctor.model.Doctor;
import com.hospital.Doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody DoctorRequest doctor) {
        return ResponseEntity.ok(this.doctorService.create(doctor));
    }
}
