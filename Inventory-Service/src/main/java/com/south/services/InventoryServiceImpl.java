package com.south.services;

import com.south.clients.BookServiceClient;
import com.south.clients.LocationServiceClient;
import com.south.exceptions.ResourceNotFoundException;
import com.south.models.BookDto;
import com.south.models.BranchDto;
import com.south.models.Inventory;
import com.south.repositories.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final BookServiceClient bookClient;
    private final LocationServiceClient branchClient;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, BookServiceClient bookClient, LocationServiceClient branchClient) {
        this.inventoryRepository = inventoryRepository;
        this.bookClient = bookClient;
        this.branchClient = branchClient;
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        // check for book in book service
        BookDto book = bookClient.getBookById(inventory.getBookId());
        if (book == null) {
            throw new ResourceNotFoundException("Book not found with id " + inventory.getBookId());
        }
        // check for branch in branch service
        BranchDto branch = branchClient.getBranchById(inventory.getBranchId());
        if (branch == null) {
            throw new ResourceNotFoundException("Branch not found with id " + inventory.getBranchId());
        }

        inventory.setLastUpdated(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id " + id));
    }

    @Override
    public Inventory getInventoryByBookIdAndBranchId(Long bookId, Long branchId) {
        Inventory inventory = inventoryRepository.findByBookIdAndBranchId(bookId, branchId);
        if (inventory == null) {
            throw new ResourceNotFoundException("Inventory not found for bookId " + bookId + " and branchId " + branchId);
        }
        return inventory;
    }

    @Override
    public List<Inventory> getInventoryByBookId(Long bookId) {
        return inventoryRepository.findByBookId(bookId);
    }

    @Override
    public List<Inventory> getInventoryByBranchId(Long branchId) {
        return inventoryRepository.findByBranchId(branchId);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        Inventory inventory = getInventoryById(id);
        inventory.setQuantity(inventoryDetails.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = getInventoryById(id);
        inventoryRepository.delete(inventory);
    }

    @Override
    @Transactional
    public void deleteInventoryByBookId(Long bookId) {
        inventoryRepository.deleteByBookId(bookId);
    }

    @Override
    @Transactional
    public void deleteInventoryByBranchId(Long branchId) {
        inventoryRepository.deleteByBranchId(branchId);
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
}
