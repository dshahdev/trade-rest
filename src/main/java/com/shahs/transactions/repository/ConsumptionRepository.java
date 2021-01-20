package com.shahs.transactions.repository;

import com.shahs.transactions.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

    @Query(value = "SELECT * from consumptionView", nativeQuery = true)
    public List<Consumption> findAllConsumptionData();
 }
