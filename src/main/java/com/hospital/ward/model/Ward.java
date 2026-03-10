package com.hospital.ward.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.hospital.model.Hospital;
import com.hospital.room.model.Room;
import com.hospital.ward.enums.Specialty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialty")
    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    @JsonIgnore
    private Hospital hospital;

    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL)
    private List<Room> rooms;

    public Ward(Specialty specialty, Hospital hospital) {
        this.specialty = specialty;
        this.hospital = hospital;
    }
}
