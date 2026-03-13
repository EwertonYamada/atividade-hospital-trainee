package com.hospital.visit.model;

import com.hospital.patient.model.Patient;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "visit")
public class Visit {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String visitorName;

        @Column(nullable = false)
        private String visitorDocument;

        @Column(nullable = false)
        private LocalDateTime entryDateTime;

        private LocalDateTime exitDateTime;

        @ManyToOne
        @JoinColumn(name = "patient_id", nullable = false)
        private Patient patient;
    }

