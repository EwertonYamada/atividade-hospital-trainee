package com.hospital.admission.model;

import com.hospital.Doctor.model.Doctor;
import com.hospital.admission.enums.AdmissionStatus;
import com.hospital.bed.enums.BedType;
import com.hospital.bed.model.Bed;
import com.hospital.admission.enums.EventType;
import com.hospital.patient.model.Patient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;

    private BedType bedType;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "admitted_at")
    private Date admittedAt;

    @Column(name = "discharged_at")
    private Date dischargedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;

    public Admission(Bed bed, Patient patient, BedType bedType) {
        this.bed = bed;
        this.bedType = bedType;
        this.patient = patient;
        this.admittedAt = new Date();
        this.dischargedAt = null;
        this.status = AdmissionStatus.ACTIVE;
    }
}
