package com.seventhray.contracts.exception;

import java.util.UUID;

/**
 * Thrown when a contract cannot be found by its ID.
 * Mapped to HTTP 404 by {@link GlobalExceptionHandler}.
 */
public class ContractNotFoundException extends RuntimeException {

    public ContractNotFoundException(UUID id) {
        super("Contract not found with id: " + id);
    }
}
