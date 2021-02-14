package com.shahs.transactions.repository;

import com.shahs.transactions.model.Pnl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PNLRepository extends JpaRepository<Pnl, Long> {

    @Query(value = "SELECT p.* FROM pnl_view p WHERE p.date = date(:date)", nativeQuery = true)
    List<Pnl> findPnlForDate(@Param("date") String  date);

    @Query(value = "SELECT p.* FROM pnl_view p WHERE p.ticker = :ticker", nativeQuery = true)
    List<Pnl> findPnlForTicker(@Param("ticker") String ticker);




//  -- pnlByDate -- will give data for all dates -- grouped by date and sum of pnl
//    select date, sum(pnl) from pnl_view group by date;
//
//    -- pnlByTicker -- will give data for all dates -- grouped by Ticker and sum of pnl
//    select ticker, sum(pnl) from pnl_view group by ticker;
//
//    -- pnlForDateByTicker -- will give data for one date -- grouped by ticker and sum of pnl
//
//    select ticker, sum(pnl) from pnl_view where date = '2020/11/25' group by ticker;
//
//    -- pnlForTickerByDate -- will give data for one Ticker -- grouped by date and sum of pnl
//
//    select date, sum(pnl) from pnl_view where ticker = 'NNDM' group by date;

}
