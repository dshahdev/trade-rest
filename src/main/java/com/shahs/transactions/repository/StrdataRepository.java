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

    @Query(value = "SELECT distinct ticker as strdata from trade", nativeQuery = true)
    List<Strdata> getAllTickers();

}


