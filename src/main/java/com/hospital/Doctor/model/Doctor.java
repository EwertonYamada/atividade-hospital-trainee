package com.hospital.Doctor.model;

import com.hospital.ward.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
