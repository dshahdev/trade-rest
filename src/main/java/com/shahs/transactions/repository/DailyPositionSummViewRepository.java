package com.shahs.transactions.repository;

import com.shahs.transactions.model.DailyPositionSummView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyPositionSummViewRepository  extends JpaRepository<DailyPositionSummView, String> {

    @Query(value = "SELECT * from daily_position_summary;", nativeQuery = true)
    List<DailyPositionSummView> getDailyPositionSummary();
}
