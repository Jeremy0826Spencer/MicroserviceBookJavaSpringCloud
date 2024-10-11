package com.south.controllers;

import com.south.models.Inventory;
import com.south.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Create a new inventory record
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@Valid @RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.createInventory(inventory);
        return ResponseEntity.ok(savedInventory);
    }

    // Get inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    // Get inventory by bookId and branchId
    @GetMapping("/book/{bookId}/branch/{branchId}")
    public ResponseEntity<Inventory> getInventoryByBookAndBranch(@PathVariable Long bookId, @PathVariable Long branchId) {
        Inventory inventory = inventoryService.getInventoryByBookIdAndBranchId(bookId, branchId);
        return ResponseEntity.ok(inventory);
    }

    // Get inventory by branchId
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<Inventory>> getInventoryByBranchId(@PathVariable Long branchId) {
        List<Inventory> inventories = inventoryService.getInventoryByBranchId(branchId);
        return ResponseEntity.ok(inventories);
    }

    // Update inventory
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @Valid @RequestBody Inventory inventoryDetails) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventoryDetails);
        return ResponseEntity.ok(updatedInventory);
    }

    // Delete inventory by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    //Get all inventories
    @GetMapping()
    public ResponseEntity<List<Inventory>> getAllInventories(){
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

}
