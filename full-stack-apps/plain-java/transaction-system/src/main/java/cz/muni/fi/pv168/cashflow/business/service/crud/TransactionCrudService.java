package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.repository.TransactionRepository;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Transaction} entity.
 */
public class TransactionCrudService implements CrudService<Transaction> {

    private final TransactionRepository transactionRepository;
    private final Validator<Transaction> transactionValidator;
    private final GuidProvider guidProvider;

    public TransactionCrudService(TransactionRepository transactionRepository,
                                  Validator<Transaction> transactionValidator,
                                  GuidProvider guidProvider) {
        this.transactionRepository = transactionRepository;
        this.transactionValidator = transactionValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public ValidationResult create(Transaction newEntity) {
        var validationResult = transactionValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (transactionRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Transaction with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            transactionRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Transaction entity) {
        var validationResult = transactionValidator.validate(entity);
        if (validationResult.isValid()) {
            transactionRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        transactionRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        transactionRepository.deleteAll();
    }
}
