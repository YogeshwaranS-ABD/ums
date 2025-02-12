package com.i2i.ums.exception;

import java.util.List;

public class ConstrainViolationException extends RuntimeException {
    private List<List<String>> violations;
    public ConstrainViolationException(String message) {
        super(message);
    }
    public ConstrainViolationException(List<List<String>> violations) {
        this.violations = violations;
    }
    public List<List<String>> getViolations() {
        return violations;
    }
}
