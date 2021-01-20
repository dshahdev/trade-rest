package com.shahs.transactions.repository;

import com.shahs.transactions.model.PNLDateSummary;
import com.shahs.transactions.model.PNLMonthSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@org.springframework.stereotype.Repository
public interface MonthSummaryRepository extends JpaRepository<PNLMonthSummary, String> {
        //  pnlByMonth -- will give data for all months -- grouped by month and sum of pnl
        @Query(value = "SELECT date_format(date,'%Y%m') as month, sum(pnl) as pnl from pnl_view group by date_format(date,'%Y%m') ", nativeQuery = true)
        List<PNLMonthSummary>getAllMonthSummary();

        @Query(value = "select concat(MONTHNAME(date), ' ', YEAR(date)) as month, sum(pnl) as pnl from pnl_view group by concat(MONTHNAME(date), ' ', YEAR(date))", nativeQuery = true)
        List<PNLMonthSummary>getPnlMonthlySummary();

}

