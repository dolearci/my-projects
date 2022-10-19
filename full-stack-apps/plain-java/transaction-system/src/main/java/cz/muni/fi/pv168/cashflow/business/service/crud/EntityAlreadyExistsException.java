package cz.muni.fi.pv168.cashflow.business.service.crud;

/**
 * Thrown, if there is already an existing entity.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
