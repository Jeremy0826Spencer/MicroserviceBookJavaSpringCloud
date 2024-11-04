package com.south.controllers;

import com.south.models.Inventory;
import com.south.services.InventoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody Inventory inventory) {
        logger.info("Creating inventory for book ID: {} and branch ID: {}", inventory.getBookId(), inventory.getBranchId());
        Inventory savedInventory = inventoryService.createInventory(inventory);
        logger.info("Inventory created with ID: {}", savedInventory.getId());
        return ResponseEntity.ok(savedInventory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        logger.info("Fetching inventory with ID: {}", id);
        Inventory inventory = inventoryService.getInventoryById(id);
        logger.info("Returning inventory: {}", inventory);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/book/{bookId}/branch/{branchId}")
    public ResponseEntity<Inventory> getInventoryByBookAndBranch(@PathVariable Long bookId, @PathVariable Long branchId) {
        logger.info("Fetching inventory for book ID: {} and branch ID: {}", bookId, branchId);
        Inventory inventory = inventoryService.getInventoryByBookIdAndBranchId(bookId, branchId);
        logger.info("Returning inventory: {}", inventory);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Inventory>> getInventoryByBranchId(@PathVariable Long branchId) {
        logger.info("Fetching inventory for branch ID: {}", branchId);
        List<Inventory> inventories = inventoryService.getInventoryByBranchId(branchId);
        logger.info("Returning {} inventories for branch ID: {}", inventories.size(), branchId);
        return ResponseEntity.ok(inventories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @Valid @RequestBody Inventory inventoryDetails) {
        logger.info("Updating inventory with ID: {}", id);
        Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        logger.info("Inventory with ID: {} updated successfully", id);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        logger.info("Deleting inventory with ID: {}", id);
        inventoryService.deleteInventory(id);
        logger.info("Inventory with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        logger.info("Fetching all inventories");
        List<Inventory> inventories = inventoryService.getAllInventories();
        logger.info("Returning {} inventories", inventories.size());
        return ResponseEntity.ok(inventories);
    }
}
