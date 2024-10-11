package com.south.clients;

import com.south.models.BranchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "location-service")
//This client is used in InventoryServiceImpl to check for the branch
//before creating the inventory
public interface LocationServiceClient {
    @GetMapping("/branches/{id}")
    BranchDto getBranchById(@PathVariable("id") Long id);
}
