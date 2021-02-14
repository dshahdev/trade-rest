package com.shahs.transactions.repository;

import com.shahs.transactions.model.Pnl;
import com.shahs.transactions.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(value = "SELECT p.* from position p  WHERE p.position_date = date(:positionDate)", nativeQuery = true)
    public List<Position> findEODPositionsForDate(@Param("positionDate") String positionDate);

    @Query(value = "SELECT p.* from position p  WHERE p.position_date = (select previous_date from previous_date_view where date = date(:positionDate))", nativeQuery = true)
    public List<Position> findSODPositionsForDate(@Param("positionDate") String positionDate);

}
