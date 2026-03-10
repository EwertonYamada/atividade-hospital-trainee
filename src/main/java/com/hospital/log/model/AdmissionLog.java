package com.hospital.log.model;

import com.hospital.bed.model.Bed;
import com.hospital.log.enums.EventType;
import com.hospital.patient.model.Patient;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class AdmissionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "date")
    private Date date;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;
}
