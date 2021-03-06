package com.shahs.transactions.repository;

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

}
