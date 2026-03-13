package com.hospital.admission.controller;

import com.hospital.admission.dto.AdmissionRequest;
import com.hospital.admission.model.Admission;
import com.hospital.admission.service.AdmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admission")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @PostMapping
    public ResponseEntity<Admission> admission(@RequestBody AdmissionRequest admissionRequest) {
        return ResponseEntity.ok(this.admissionService.admission(admissionRequest));
    }

    @PostMapping("/{admissionId}/discharge")
    public ResponseEntity<Admission> discharge(@PathVariable Long admissionId) {
        return ResponseEntity.ok(this.admissionService.discharge(admissionId));
    }
}
