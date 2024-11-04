package com.south.services;

import com.south.exceptions.ResourceNotFoundException;
import com.south.models.Branch;
import com.south.models.BranchDeletedEvent;
import com.south.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Import SLF4J Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;
    private final StreamBridge streamBridge;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, StreamBridge streamBridge) {
        this.branchRepository = branchRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public Branch createBranch(Branch branch) {
        logger.info("Creating a new branch with name: {}", branch.getName());
        Branch savedBranch = branchRepository.save(branch);
        logger.info("Branch created successfully with ID: {}", savedBranch.getId());
        return savedBranch;
    }

    @Override
    public Branch getBranchById(Long id) {
        logger.debug("Fetching branch with ID: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Branch not found with ID: {}", id);
                    return new ResourceNotFoundException("Branch not found with id " + id);
                });
        logger.debug("Branch retrieved: {}", branch);
        return branch;
    }

    @Override
    public List<Branch> getAllBranches() {
        logger.info("Fetching all branches");
        List<Branch> branches = branchRepository.findAll();
        logger.info("Number of branches retrieved: {}", branches.size());
        return branches;
    }

    @Override
    public Branch updateBranch(Long id, Branch branchDetails) {
        logger.info("Updating branch with ID: {}", id);
        Branch branch = getBranchById(id);
        branch.setName(branchDetails.getName());
        branch.setAddress(branchDetails.getAddress());
        branch.setCity(branchDetails.getCity());
        branch.setState(branchDetails.getState());
        branch.setPostalCode(branchDetails.getPostalCode());
        branch.setCountry(branchDetails.getCountry());
        Branch updatedBranch = branchRepository.save(branch);
        logger.info("Branch updated successfully: {}", updatedBranch);
        return updatedBranch;
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        logger.info("Deleting branch with ID: {}", id);
        Branch branch = getBranchById(id);
        branchRepository.delete(branch);
        logger.info("Branch deleted successfully with ID: {}", id);

        // Publish BranchDeletedEvent to Kafka
        BranchDeletedEvent event = new BranchDeletedEvent(branch.getId());
        logger.debug("Publishing BranchDeletedEvent: {}", event);
        boolean sent = streamBridge.send("branchDeleted-out-0", event);
        if (sent) {
            logger.info("BranchDeletedEvent sent successfully for branch ID: {}", branch.getId());
        } else {
            logger.warn("Failed to send BranchDeletedEvent for branch ID: {}", branch.getId());
        }
    }
}
