package com.shahs.transactions.repository;

import com.shahs.transactions.model.DateSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateSummaryRepository extends JpaRepository<DateSummary, Long> {
}
