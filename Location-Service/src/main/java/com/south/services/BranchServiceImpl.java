package com.south.services;

import com.south.exceptions.ResourceNotFoundException;
import com.south.models.Branch;
import com.south.models.BranchDeletedEvent;
import com.south.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final StreamBridge streamBridge;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, StreamBridge streamBridge) {
        this.branchRepository = branchRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id " + id));
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch updateBranch(Long id, Branch branchDetails) {
        Branch branch = getBranchById(id);
        branch.setName(branchDetails.getName());
        branch.setAddress(branchDetails.getAddress());
        branch.setCity(branchDetails.getCity());
        branch.setState(branchDetails.getState());
        branch.setPostalCode(branchDetails.getPostalCode());
        branch.setCountry(branchDetails.getCountry());
        return branchRepository.save(branch);
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        Branch branch = getBranchById(id);
        branchRepository.delete(branch);

        // Publish BranchDeletedEvent to Kafka
        BranchDeletedEvent event = new BranchDeletedEvent(branch.getId());
        streamBridge.send("branchDeleted-out-0", event);

    }
}
