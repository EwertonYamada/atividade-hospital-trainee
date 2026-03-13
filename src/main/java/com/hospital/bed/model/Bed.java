package com.hospital.bed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.bed.enums.BedStatus;
import com.hospital.bed.enums.BedType;
import com.hospital.room.model.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BedStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "bedType must be defined")
    private BedType bedType;

    public Bed(Room room, Integer nextBedNumber, @NotBlank BedType bedType) {
        this.room = room;
        this.bedNumber = nextBedNumber;
        this.bedType = bedType;
        this.status = BedStatus.UNOCCUPIED;
    }
}
