-- Schema for Contracts Management System Module

-- Enable UUID extension if not enabled (useful for local setup)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Contracts Table
CREATE TABLE contracts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Workflow History Table
CREATE TABLE workflow_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    contract_id UUID NOT NULL,
    previous_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,
    changed_by VARCHAR(255) NOT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_workflow_history_contract FOREIGN KEY (contract_id) REFERENCES contracts(id) ON DELETE CASCADE
);

-- Indexes for performance optimization
-- Speed up filters by contract status
CREATE INDEX idx_contracts_status ON contracts(status);

-- Speed up search by title (case-insensitive searches often use expression indexes or lower())
CREATE INDEX idx_contracts_title ON contracts(lower(title));

-- Speed up search by owner (case-insensitive)
CREATE INDEX idx_contracts_owner ON contracts(lower(owner_name));

-- Speed up workflow history queries filtering by contract_id
CREATE INDEX idx_workflow_history_contract_id ON workflow_history(contract_id);
