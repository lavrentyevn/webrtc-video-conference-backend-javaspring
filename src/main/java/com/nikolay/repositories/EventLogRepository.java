package com.nikolay.repositories;

import com.nikolay.models.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Integer> {
    Optional<EventLog> findFirstByEventIdAndUserModelEmailOrderByIdDesc(Integer id, String email);
}
