package com.hospital.bed.repository;

import com.hospital.bed.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRepository extends JpaRepository<Bed, Integer> {

    @Query(nativeQuery = true, value = """
    SELECT
        COALESCE(MAX(LAST), 0)
    FROM
        (
        SELECT
            bed_number AS LAST
        FROM
            bed b
        JOIN room r
            ON r.id = b.room_id 
                AND r.id = :roomId
        ) lastRoomCode
    """)
    Integer findLastBedNumber(@Param("roomId") Long roomId);
}
