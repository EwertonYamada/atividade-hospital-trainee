package com.hospital.bed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.bed.enums.BedType;
import com.hospital.room.model.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bed {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "bed_number")
    private Integer bedNumber;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BedType bedType;

    public Bed(Room room, Integer nextBedNumber, Boolean aTrue, BedType bedType) {
        this.room = room;
        this.bedNumber = nextBedNumber;
        this.isAvailable = aTrue;
        this.bedType = bedType;
    }
}
