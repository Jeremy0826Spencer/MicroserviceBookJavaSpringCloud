package com.south.clients;

import com.south.models.BranchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service")
public interface LocationServiceClient {

    Logger logger = LoggerFactory.getLogger(LocationServiceClient.class);

    @GetMapping("/branches/{id}")
    default BranchDto getBranchById(@PathVariable("id") Long id) {
        logger.info("Fetching branch with ID: {}", id);
        return getBranchByIdInternal(id);
    }

    @GetMapping("/branches/{id}")
    BranchDto getBranchByIdInternal(@PathVariable("id") Long id);
}