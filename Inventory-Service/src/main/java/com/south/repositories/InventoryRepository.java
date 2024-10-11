package com.south.repositories;

import com.south.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByBookIdAndBranchId(Long bookId, Long branchId);
    List<Inventory> findByBookId(Long bookId);
    List<Inventory> findByBranchId(Long branchId);
    void deleteByBookId(Long bookId);
    void deleteByBranchId(Long branchId);
}

