package com.hospital.Doctor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hospital.Doctor.dto.DoctorRequest;
import com.hospital.admission.model.Admission;
import com.hospital.ward.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

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
    @NonNull
    private String name;

    @Column(name = "crm", unique = true)
    @NonNull
    private String crm;

    @Column(name = "specialty")
    @Enumerated(EnumType.STRING)
    @NonNull
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
