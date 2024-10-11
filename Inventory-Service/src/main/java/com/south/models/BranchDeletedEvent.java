package com.south.models;

public class BranchDeletedEvent {

    private Long branchId;

    public BranchDeletedEvent() {}

    public BranchDeletedEvent(Long branchId) {
        this.branchId = branchId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
