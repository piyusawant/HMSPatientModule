package com.gtservices.hms.report.repository;

import com.gtservices.hms.report.entity.DocumentStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentStoreRepository extends JpaRepository<DocumentStore, Integer> {
}
