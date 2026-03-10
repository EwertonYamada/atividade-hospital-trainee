package com.hospital.room.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.bed.model.Bed;
import com.hospital.ward.model.Ward;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_code")
    private String roomCode;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    @JsonIgnore
    private Ward ward;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Bed> beds;

    public Room(String roomCode, Ward ward) {
        this.roomCode = roomCode;
        this.ward = ward;
    }
}
