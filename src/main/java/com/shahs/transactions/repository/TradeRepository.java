package com.shahs.transactions.repository;

import com.shahs.transactions.model.Position;
import com.shahs.transactions.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query(value = "SELECT t FROM Trade t WHERE t.action = :action and t.date = Date(:date)")
    List<Trade> findTradesForDateByAction(@Param("date") String date, @Param("action") String action);

    @Query(value = "select * from Trade t where t.id in (select distinct buy_id from position p where position_date = (select previous_date from previous_date_view where date = date(:positionDate)) and available_qty > 0);", nativeQuery = true)
    public List<Trade> findBuyTradesForOpenPositions(@Param("positionDate") String positionDate);

}
