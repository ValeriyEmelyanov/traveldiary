package com.example.traveldiary.repository;

import com.example.traveldiary.model.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
}
