package com.south.services;

import com.south.models.BookDeletedEvent;
import com.south.models.BranchDeletedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventListener {

    @Autowired
    private InventoryService inventoryService;
    @Bean
    public java.util.function.Consumer<BookDeletedEvent> bookDeleted() {
        return event -> {
            inventoryService.deleteInventoryByBookId(event.getBookId());
        };
    }

    @Bean
    public java.util.function.Consumer<BranchDeletedEvent> branchDeleted() {
        return event -> {
            inventoryService.deleteInventoryByBranchId(event.getBranchId());
        };
    }
}


