package com.shahs.transactions.repository;

import com.shahs.transactions.model.PNLDateSummary;
import com.shahs.transactions.model.PNLTickerSummary;
import com.shahs.transactions.model.Pnl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.sql.Date;
import java.util.List;

@org.springframework.stereotype.Repository
public interface PNLTickerRepository extends JpaRepository<PNLTickerSummary, String> {
    @Query(value = "SELECT ticker, sum(pnl) as pnl FROM pnl_view group by ticker", nativeQuery = true)
    List<PNLTickerSummary> findPnlByTicker();

    @Query(value = "SELECT ticker, sum(pnl) as pnl FROM pnl_view p WHERE p.date =  date(:date) group by ticker", nativeQuery = true)
    List<PNLTickerSummary> findPnlForDateByTicker(@Param("date") Date date);

    @Query(value = "SELECT ticker, sum(pnl) as pnl FROM pnl_view p where concat(MONTHNAME(date), ' ', YEAR(date)) = :monthstr group by ticker;", nativeQuery = true)
    List<PNLTickerSummary> findPnlForMonthByTicker(@Param("monthstr") String monthstr);


}
