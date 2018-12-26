package ru.otus.sua.L12.appSecure.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public abstract class EnterpriseException extends RuntimeException {

    public EnterpriseException() {
        super();
    }

    public EnterpriseException(String message) {
        super(message);
    }

    public EnterpriseException(Throwable cause) {
        super(cause);
    }

    public EnterpriseException(String message, Throwable cause) {
        super(message, cause);
    }
}
