package com.south.services;

import com.south.models.Branch;

import java.util.List;
public interface BranchService {
    Branch createBranch(Branch branch);
    Branch getBranchById(Long id);
    List<Branch> getAllBranches();
    Branch updateBranch(Long id, Branch branchDetails);
    void deleteBranch(Long id);
}
