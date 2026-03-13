package com.hospital.Doctor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.Doctor.dto.DoctorRequest;
import com.hospital.admission.model.Admission;
import com.hospital.ward.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "crm")
    private String crm;

    @Column(name = "specialty")
    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @ManyToMany(mappedBy = "doctors")
    @JsonIgnore
    private List<Admission> admissions = new ArrayList<>();

    public Doctor(DoctorRequest doctorRequest) {
        this.name = doctorRequest.name();
        this.crm = doctorRequest.crm();
        this.specialty = doctorRequest.specialty();
    }

}
