package com.seventhray.contracts.repository;

import com.seventhray.contracts.model.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    /**
     * Flexible search supporting:
     * <ul>
     *   <li>Case-insensitive partial match on title OR ownerName when {@code search} is provided.</li>
     *   <li>Exact (case-normalised) match on status when {@code status} is provided.</li>
     *   <li>No filter applied when either parameter is {@code null}.</li>
     * </ul>
     * Ordering is delegated to the {@link Pageable} argument (default: createdAt DESC).
     */
    @Query("""
            SELECT c FROM Contract c
            WHERE (:search = ''
                   OR LOWER(c.title)     LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.ownerName) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status = '' OR c.status = :status)
            """)
    Page<Contract> findByFilters(
            @Param("search") String search,
            @Param("status") String status,
            Pageable pageable
    );
}
