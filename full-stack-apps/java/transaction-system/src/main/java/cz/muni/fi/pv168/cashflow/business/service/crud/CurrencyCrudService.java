package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.repository.CurrencyRepository;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Currency} entity.
 */
public class CurrencyCrudService implements CrudService<Currency> {

    private final CurrencyRepository currencyRepository;
    private final Validator<Currency> currencyValidator;
    private final GuidProvider guidProvider;

    public CurrencyCrudService(CurrencyRepository currencyRepository,
                               Validator<Currency> currencyValidator,
                               GuidProvider guidProvider) {
        this.currencyRepository = currencyRepository;
        this.currencyValidator = currencyValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public ValidationResult create(Currency newEntity) {
        var validationResult = currencyValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (currencyRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Currency with given guid already exists: " + newEntity.getGuid());
        }

        if (currencyRepository.existsByName(newEntity.getName())) {
            throw new EntityAlreadyExistsException("Currency with given name but different code already exists: " + newEntity.getName());
        }

        if (currencyRepository.existsByCode(newEntity.getCode())) {
            throw new EntityAlreadyExistsException("Currency with given code but different name already exists: " + newEntity.getCode());
        }

        if (validationResult.isValid()) {
            currencyRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Currency entity) {
        var validationResult = currencyValidator.validate(entity);
        if (validationResult.isValid()) {
            currencyRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        currencyRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        currencyRepository.deleteAll();
    }
}
