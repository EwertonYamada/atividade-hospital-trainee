package com.hospital.visit.controller;

import com.hospital.visit.dto.VisitRequest;
import com.hospital.visit.model.Visit;
import com.hospital.visit.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;


    @PostMapping("/start")
    public ResponseEntity<Visit> registerEntry(@Valid @RequestBody VisitRequest request) {
        Visit visit = visitService.startVisit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(visit);
    }


    @PutMapping("/{id}/end")
    public ResponseEntity<Visit> registerExit(@PathVariable Long id) {
        Visit visit = visitService.endVisit(id);
        return ResponseEntity.ok(visit);
    }
}