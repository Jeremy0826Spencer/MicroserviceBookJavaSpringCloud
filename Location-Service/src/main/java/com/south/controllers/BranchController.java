package com.south.controllers;

import com.south.models.Branch;
import com.south.services.BranchService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // Create a new branch
    @PostMapping
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody Branch branch) {
        logger.info("Received request to create branch: {}", branch.getName());
        Branch createdBranch = branchService.createBranch(branch);
        logger.info("Branch created with ID: {}", createdBranch.getId());
        return ResponseEntity.ok(createdBranch);
    }

    // Get branch by ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        logger.info("Received request to get branch with ID: {}", id);
        Branch branch = branchService.getBranchById(id);
        logger.info("Returning branch: {}", branch);
        return ResponseEntity.ok(branch);
    }

    // Get all branches
    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        logger.info("Received request to get all branches");
        List<Branch> branches = branchService.getAllBranches();
        logger.info("Returning {} branches", branches.size());
        return ResponseEntity.ok(branches);
    }

    // Update a branch
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @Valid @RequestBody Branch branchDetails) {
        logger.info("Received request to update branch with ID: {}", id);
        Branch updatedBranch = branchService.updateBranch(id, branchDetails);
        logger.info("Branch with ID: {} updated successfully", id);
        return ResponseEntity.ok(updatedBranch);
    }

    // Delete a branch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        logger.info("Received request to delete branch with ID: {}", id);
        branchService.deleteBranch(id);
        logger.info("Branch with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
