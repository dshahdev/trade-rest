package com.shahs.transactions.repository;

import com.shahs.transactions.model.PortfolioReturn;
import com.shahs.transactions.model.PositionForDateByTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface PositionForDateByTickerRepo extends JpaRepository<PositionForDateByTicker, Long> {

    @Query(value = " Select  DATE_FORMAT(pdtv.position_date, '%Y-%m-%d') AS position_date, pdtv.ticker, pdtv.original_qty, pdtv.available_qty, pdtv.allocated_qty, round(pdtv.mv,0) as mv," +
            "        round(pdtv.cost,0) as cost, round(pdtv.unrealized, 0) as unrealized, round(pdtv.prior_price,2) as prior_price, round(pdtv.cur_price, 2) as cur_price " +
            "        from position_date_ticker_view pdtv where pdtv.position_date = DATE(:date)", nativeQuery = true)
    List<PositionForDateByTicker> getPotionForDateByTicker(@Param("date") String date);
}
