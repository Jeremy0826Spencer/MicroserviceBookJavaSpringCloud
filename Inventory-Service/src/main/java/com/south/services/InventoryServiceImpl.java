package com.south.services;

import com.south.clients.BookServiceClient;
import com.south.clients.LocationServiceClient;
import com.south.exceptions.ResourceNotFoundException;
import com.south.models.BookDto;
import com.south.models.BranchDto;
import com.south.models.Inventory;
import com.south.repositories.InventoryRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

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
        logger.info("Creating inventory for book ID: {} and branch ID: {}", inventory.getBookId(), inventory.getBranchId());
        BookDto book = bookClient.getBookById(inventory.getBookId());
        if (book == null) {
            logger.error("Book not found with ID: {}", inventory.getBookId());
            throw new ResourceNotFoundException("Book not found with id " + inventory.getBookId());
        }
        BranchDto branch = branchClient.getBranchById(inventory.getBranchId());
        if (branch == null) {
            logger.error("Branch not found with ID: {}", inventory.getBranchId());
            throw new ResourceNotFoundException("Branch not found with id " + inventory.getBranchId());
        }

        inventory.setLastUpdated(LocalDateTime.now());
        Inventory savedInventory = inventoryRepository.save(inventory);
        logger.info("Inventory created successfully with ID: {}", savedInventory.getId());
        return savedInventory;
    }

    @Override
    public Inventory getInventoryById(Long id) {
        logger.info("Fetching inventory with ID: {}", id);
        return inventoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Inventory not found with ID: {}", id);
                    return new ResourceNotFoundException("Inventory not found with id " + id);
                });
    }

    @Override
    public Inventory getInventoryByBookIdAndBranchId(Long bookId, Long branchId) {
        logger.info("Fetching inventory for book ID: {} and branch ID: {}", bookId, branchId);
        Inventory inventory = inventoryRepository.findByBookIdAndBranchId(bookId, branchId);
        if (inventory == null) {
            logger.error("Inventory not found for book ID: {} and branch ID: {}", bookId, branchId);
            throw new ResourceNotFoundException("Inventory not found for bookId " + bookId + " and branchId " + branchId);
        }
        return inventory;
    }

    @Override
    public List<Inventory> getInventoryByBookId(Long bookId) {
        logger.info("Fetching inventory for book ID: {}", bookId);
        return inventoryRepository.findByBookId(bookId);
    }

    @Override
    public List<Inventory> getInventoryByBranchId(Long branchId) {
        logger.info("Fetching inventory for branch ID: {}", branchId);
        return inventoryRepository.findByBranchId(branchId);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventoryDetails) {
        logger.info("Updating inventory with ID: {}", id);
        Inventory inventory = getInventoryById(id);
        inventory.setQuantity(inventoryDetails.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());
        Inventory updatedInventory = inventoryRepository.save(inventory);
        logger.info("Inventory with ID: {} updated successfully", id);
        return updatedInventory;
    }


    @Override
    public void deleteInventory(Long id) {
        logger.info("Deleting inventory with ID: {}", id);
        Inventory inventory = getInventoryById(id);
        inventoryRepository.delete(inventory);
        logger.info("Inventory with ID: {} deleted successfully", id);
    }

    @Override
    @Transactional
    public void deleteInventoryByBookId(Long bookId) {
        logger.info("Deleting inventory for book ID: {}", bookId);
        inventoryRepository.deleteByBookId(bookId);
        logger.info("Inventory for book ID: {} deleted successfully", bookId);
    }

    @Override
    @Transactional
    public void deleteInventoryByBranchId(Long branchId) {
        logger.info("Deleting inventory for branch ID: {}", branchId);
        inventoryRepository.deleteByBranchId(branchId);
        logger.info("Inventory for branch ID: {} deleted successfully", branchId);
    }

    @Override
    public List<Inventory> getAllInventories() {
        logger.info("Fetching all inventories");
        return inventoryRepository.findAll();
    }
}
