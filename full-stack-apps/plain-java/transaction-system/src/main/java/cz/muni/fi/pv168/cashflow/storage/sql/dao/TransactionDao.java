package cz.muni.fi.pv168.cashflow.storage.sql.dao;

import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.TransactionEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class TransactionDao implements DataAccessObject<TransactionEntity> {

    private final Supplier<ConnectionHandler> connections;

    public TransactionDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    private static TransactionEntity transactionFromResultSet(ResultSet resultSet) throws SQLException {
        return new TransactionEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getString("receiver"),
                TransactionType.valueOf(resultSet.getString("type")),
                resultSet.getDouble("amount"),
                resultSet.getLong("currencyId"),
                resultSet.getLong("categoryId"),
                resultSet.getTimestamp("date").toLocalDateTime().toLocalDate(),
                resultSet.getString("variableSymbol"),
                resultSet.getString("constantSymbol"),
                resultSet.getString("specificSymbol"),
                resultSet.getString("message")
        );
    }

    @Override
    public TransactionEntity create(TransactionEntity newTransaction) {
        var sql = """ 
                INSERT INTO Transaction (
                    guid,
                    receiver,
                    type,
                    amount,
                    currencyId,
                    categoryId,
                    date,
                    variableSymbol,
                    constantSymbol,
                    specificSymbol,
                    message
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newTransaction.guid());
            statement.setString(2, newTransaction.receiver());
            statement.setString(3, newTransaction.transactionType().toString());
            statement.setDouble(4, newTransaction.amount());
            statement.setLong(5, newTransaction.currencyId());
            statement.setLong(6, newTransaction.categoryId());
            statement.setDate(7, Date.valueOf(newTransaction.date()));
            statement.setString(8, newTransaction.variableSymbol());
            statement.setString(9, newTransaction.constantSymbol());
            statement.setString(10, newTransaction.specificSymbol());
            statement.setString(11, newTransaction.message());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long transactionId;

                if (keyResultSet.next()) {
                    transactionId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newTransaction);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newTransaction);
                }

                return findById(transactionId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newTransaction, ex);
        }
    }

    @Override
    public Collection<TransactionEntity> findAll() {
        var sql = """
                SELECT  id,
                        guid,
                        receiver,
                        type,
                        amount,
                        currencyId,
                        categoryId,
                        date,
                        variableSymbol,
                        constantSymbol,
                        specificSymbol,
                        message
                FROM Transaction
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            List<TransactionEntity> transactions = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var transaction = transactionFromResultSet(resultSet);
                    transactions.add(transaction);
                }
            }

            return transactions;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all transactions", ex);
        }
    }

    @Override
    public Optional<TransactionEntity> findById(long id) {
        var sql = """
                SELECT  id,
                        guid,
                        receiver,
                        type,
                        amount,
                        currencyId,
                        categoryId,
                        date,
                        variableSymbol,
                        constantSymbol,
                        specificSymbol,
                        message
                FROM Transaction
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(transactionFromResultSet(resultSet));
            } else {
                // transaction not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load transaction by id: " + id, ex);
        }
    }

    @Override
    public Optional<TransactionEntity> findByGuid(String guid) {
        var sql = """
                SELECT  id,
                        guid,
                        receiver,
                        type,
                        amount,
                        currencyId,
                        categoryId,
                        date,
                        variableSymbol,
                        constantSymbol,
                        specificSymbol,
                        message
                FROM Transaction
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(transactionFromResultSet(resultSet));
            } else {
                // transaction not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load transaction by guid: " + guid, ex);
        }
    }

    @Override
    public TransactionEntity update(TransactionEntity entity) {
        var sql = """
                UPDATE Transaction
                SET receiver = ?,
                    type = ?,
                    amount = ?,
                    currencyId = ?,
                    categoryId = ?,
                    date = ?,
                    variableSymbol = ?,
                    constantSymbol = ?,
                    specificSymbol = ?,
                    message = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.receiver());
            statement.setString(2, entity.transactionType().toString());
            statement.setDouble(3, entity.amount());
            statement.setLong(4, entity.currencyId());
            statement.setLong(5, entity.categoryId());
            statement.setDate(6, Date.valueOf(entity.date()));
            statement.setString(7, entity.variableSymbol());
            statement.setString(8, entity.constantSymbol());
            statement.setString(9, entity.specificSymbol());
            statement.setString(10, entity.message());
            statement.setLong(11, entity.id());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Transaction not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 transactions (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update Transaction: " + entity, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = """
                DELETE FROM Transaction
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Transaction not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 transactions (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete Transaction, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Transaction";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all transactions", ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM Transaction
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            try (var resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if Transaction exists, guid: " + guid, ex);
        }
    }
}
