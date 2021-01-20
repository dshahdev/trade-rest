package com.shahs.transactions.repository;

import com.shahs.transactions.model.PNLDateSummary;
import com.shahs.transactions.model.PNLTickerSummary;
import com.shahs.transactions.model.Strdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


@org.springframework.stereotype.Repository
public interface PNLDateRepository extends JpaRepository<PNLDateSummary, Date> {

    //    	-- pnlByDate -- will give data for all dates -- grouped by date and sum of pnl
    @Query(value = "SELECT Date(date) as date, sum(pnl) as pnl FROM pnl_view group by date", nativeQuery = true)
    List<com.shahs.transactions.model.PNLDateSummary> findPnlByDate();

    @Query(value = "SELECT date, sum(pnl) as pnl FROM pnl_view p where p.ticker = :ticker group by date;", nativeQuery = true)
    List<PNLDateSummary> findPnlForTickerByDate(@Param("ticker") String ticker);

    @Query(value = "SELECT date, sum(pnl) as pnl FROM pnl_view p where concat(MONTHNAME(date), ' ', YEAR(date)) = :monthstr group by date;", nativeQuery = true)
    List<PNLDateSummary> findPnlForMonthByDate(@Param("monthstr") String monthstr);



    @Query(value = "SELECT date, sum(pnl) as pnl from pnl_view group by date", nativeQuery = true)
    List<PNLDateSummary>getPnlForAllDates();


}

