package com.shahs.transactions.repository;

import com.shahs.transactions.model.Pnl;
import com.shahs.transactions.model.PortfolioReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PortfolioReturnRepository extends JpaRepository<PortfolioReturn, Long> {

    @Query(value = "select prdv.* from portfolio_return_date_view prdv", nativeQuery = true)
    List<PortfolioReturn> getAll();

    @Query(value = "select prdv.* from portfolio_return_date_view prdv  where concat(MONTHNAME(position_date), ' ', YEAR(position_date)) = :monthstr", nativeQuery = true)
    List<PortfolioReturn>findPortfolioDailyReturnForMonth(@Param("monthstr") String monthstr);

}

