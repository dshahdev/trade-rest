package com.shahs.transactions.repository;

import com.shahs.transactions.model.DailyPositionSummView;
import com.shahs.transactions.model.Pnl;
import com.shahs.transactions.model.PositionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionViewRepository  extends JpaRepository<PositionView, String> {

    @Query(value = "SELECT p.* FROM position_view p WHERE p.position_date = date(:positionDate)", nativeQuery = true)
    List<PositionView> getPositionsForDate(@Param("positionDate") String positionDate);

}
