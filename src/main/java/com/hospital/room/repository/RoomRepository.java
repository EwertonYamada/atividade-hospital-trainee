package com.hospital.room.repository;

import com.hospital.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(nativeQuery = true, value = """
    SELECT
    COALESCE(MAX(last), 0)
    FROM (
        SELECT CAST(regexp_replace(room_code, '[^0-9]', '', 'g') AS INTEGER) AS last
        FROM room
        WHERE room_code ~ '[0-9]'
    ) lastRoomCode
    """)
    Integer findLastRoomNumber();
}
