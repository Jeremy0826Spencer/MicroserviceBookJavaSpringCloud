package com.south.services;

import com.south.models.Inventory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    Inventory getInventoryById(Long id);
    Inventory getInventoryByBookIdAndBranchId(Long bookId, Long branchId);
    List<Inventory> getInventoryByBookId(Long bookId);
    List<Inventory> getInventoryByBranchId(Long branchId);
    Inventory updateInventory(Long id, Inventory inventoryDetails);
    void deleteInventory(Long id);
    void deleteInventoryByBookId(Long bookId);
    void deleteInventoryByBranchId(Long branchId);
    List<Inventory> getAllInventories();
}



