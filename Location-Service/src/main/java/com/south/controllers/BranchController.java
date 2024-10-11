package com.south.controllers;

import com.south.models.Branch;
import com.south.services.BranchService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // Create a new branch
    @PostMapping
    public ResponseEntity<Branch> createBranch(@Valid @RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.createBranch(branch));
    }

    // Get branch by ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    // Get all branches
    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    // Update a branch
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @Valid @RequestBody Branch branchDetails) {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDetails));
    }

    // Delete a branch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
