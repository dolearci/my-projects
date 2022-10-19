package cz.muni.fi.pv168.cashflow.storage.sql;

import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.repository.TransactionRepository;
import cz.muni.fi.pv168.cashflow.storage.DataStorageException;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.TransactionEntity;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.EntityMapper;

import java.util.List;

public class TransactionSqlRepository implements TransactionRepository {

    private final DataAccessObject<TransactionEntity> transactionDao;
    private final EntityMapper<TransactionEntity, Transaction> transactionMapper;

    public TransactionSqlRepository(
            DataAccessObject<TransactionEntity> transactionDao,
            EntityMapper<TransactionEntity, Transaction> transactionMapper) {
        this.transactionDao = transactionDao;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll().stream().map(transactionMapper::mapToBusiness).toList();
    }

    @Override
    public void create(Transaction newEntity) {
        transactionDao.create(transactionMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Transaction entity) {
        var existingTransaction = transactionDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Department not found, guid: " + entity.getGuid()));
        var updatedTransaction = transactionMapper.mapExistingEntityToDatabase(entity, existingTransaction.id());

        transactionDao.update(updatedTransaction);

    }

    @Override
    public void deleteByGuid(String guid) {
        transactionDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        transactionDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return transactionDao.existsByGuid(guid);
    }

}
