package cz.muni.fi.pv168.cashflow.storage.sql;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.repository.CurrencyRepository;
import cz.muni.fi.pv168.cashflow.storage.DataStorageException;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.CurrencyDao;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CurrencyEntity;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.EntityMapper;

import java.util.List;

public class CurrencySqlRepository implements CurrencyRepository {

    private final CurrencyDao currencyDao;
    private final EntityMapper<CurrencyEntity, Currency> currencyMapper;

    public CurrencySqlRepository(
            CurrencyDao currencyDao,
            EntityMapper<CurrencyEntity, Currency> currencyMapper) {
        this.currencyDao = currencyDao;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public List<Currency> findAll() {
        return currencyDao.findAll().stream().map(currencyMapper::mapToBusiness).toList();
    }

    @Override
    public void create(Currency newEntity) {
        currencyDao.create(currencyMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Currency entity) {
        var existingCurrency = currencyDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Department not found, guid: " + entity.getGuid()));
        var updatedCurrency = currencyMapper.mapExistingEntityToDatabase(entity, existingCurrency.id());

        currencyDao.update(updatedCurrency);

    }

    @Override
    public void deleteByGuid(String guid) {
        currencyDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        currencyDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return currencyDao.existsByGuid(guid);
    }

    @Override
    public boolean existsByName(String name) {
        return currencyDao.existsByName(name);
    }

    @Override
    public boolean existsByCode(String code) {
        return currencyDao.existsByCode(code);
    }

}