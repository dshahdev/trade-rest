package com.shahs.transactions.repository;

import com.shahs.transactions.model.PNLDateSummary;
import com.shahs.transactions.model.PNLTickerSummary;
import com.shahs.transactions.model.Strdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StrdataRepository extends JpaRepository<Strdata, String> {
    @Query(value = "SELECT distinct concat(MONTHNAME(date), ' ', YEAR(date)) as strdata from trade union all select 'all' as strdata", nativeQuery = true)
    List<Strdata> getAllMonths();

    @Query(value = "SELECT distinct ticker as strdata from trade where ticker not in ('LVGO')", nativeQuery = true)
    List<Strdata> getAllTickers();
//
//    @Query(value = "select count(*) as data from price c where price_date = Date(:priceDate) and ticker = :ticker", nativeQuery = true)
//    public boolean existsDateAndTickerInPrice(@Param("priceDate") String priceDate, @Param("ticker") String ticker);
}


