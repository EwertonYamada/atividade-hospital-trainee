package com.hospital.bed.repository;

import com.hospital.bed.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRepository extends JpaRepository<Bed, Integer> {

    @Query(nativeQuery = true, value = """
    SELECT COALESCE(MAX(bed_number), 0) FROM bed
    """)
    Integer findLastBedNumber();
}
