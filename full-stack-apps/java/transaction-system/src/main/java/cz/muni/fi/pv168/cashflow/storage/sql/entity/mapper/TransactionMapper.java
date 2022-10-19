package cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CurrencyEntity;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.TransactionEntity;

public class TransactionMapper implements EntityMapper<TransactionEntity, Transaction> {

    private final DataAccessObject<CurrencyEntity> currencyDao;
    private final DataAccessObject<CategoryEntity> categoryDao;
    private final EntityMapper<CurrencyEntity, Currency> currencyMapper;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;

    public TransactionMapper(
            DataAccessObject<CurrencyEntity> currencyDao,
            DataAccessObject<CategoryEntity> categoryDao,
            EntityMapper<CurrencyEntity, Currency> currencyMapper,
            EntityMapper<CategoryEntity, Category> categoryMapper) {
        this.currencyDao = currencyDao;
        this.categoryDao = categoryDao;
        this.currencyMapper = currencyMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Transaction mapToBusiness(TransactionEntity entity) {
        var currency = currencyDao.findById(entity.currencyId())
                .map(currencyMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Currency not found, guid: " + entity.currencyId()));
        var category = categoryDao.findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " + entity.categoryId()));
        return new Transaction(
                entity.guid(),
                entity.receiver(),
                entity.transactionType(),
                entity.amount(),
                currency,
                category,
                entity.date(),
                entity.variableSymbol(),
                entity.constantSymbol(),
                entity.specificSymbol(),
                entity.message());
    }

    @Override
    public TransactionEntity mapNewEntityToDatabase(Transaction entity) {
        var currencyEntity = currencyDao.findByGuid(entity.getCurrency().getGuid())
                .orElseThrow(() -> new DataStorageException("Currency not found, guid: " + entity.getCurrency().getGuid()));
        var categoryEntity = categoryDao.findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " + entity.getCategory().getGuid()));
        return new TransactionEntity(
                entity.getGuid(),
                entity.getReceiver(),
                entity.getTransactionType(),
                entity.getAmount(),
                currencyEntity.id(),
                categoryEntity.id(),
                entity.getDate(),
                entity.getVariableSymbol(),
                entity.getConstantSymbol(),
                entity.getSpecificSymbol(),
                entity.getMessage());

    }

    @Override
    public TransactionEntity mapExistingEntityToDatabase(Transaction entity, Long dbId) {
        var currencyEntity = currencyDao.findByGuid(entity.getCurrency().getGuid())
                .orElseThrow(() -> new DataStorageException("Currency not found, guid: " + entity.getCurrency().getGuid()));
        var categoryEntity = categoryDao.findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " + entity.getCategory().getGuid()));
        return new TransactionEntity(
                dbId,
                entity.getGuid(),
                entity.getReceiver(),
                entity.getTransactionType(),
                entity.getAmount(),
                currencyEntity.id(),
                categoryEntity.id(),
                entity.getDate(),
                entity.getVariableSymbol(),
                entity.getConstantSymbol(),
                entity.getSpecificSymbol(),
                entity.getMessage());
    }
}

