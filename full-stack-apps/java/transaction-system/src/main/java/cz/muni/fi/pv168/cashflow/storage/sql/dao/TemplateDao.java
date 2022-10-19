package cz.muni.fi.pv168.cashflow.storage.sql.dao;

import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.TemplateEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class TemplateDao implements DataAccessObject<TemplateEntity> {

    private final Supplier<ConnectionHandler> connections;

    public TemplateDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    private static TemplateEntity templateFromResultSet(ResultSet resultSet) throws SQLException {
        return new TemplateEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getString("templateName"),
                resultSet.getString("receiver"),
                TransactionType.valueOf(resultSet.getString("type")),
                resultSet.getDouble("amount"),
                resultSet.getLong("currencyId"),
                resultSet.getLong("categoryId"),
                resultSet.getString("variableSymbol"),
                resultSet.getString("constantSymbol"),
                resultSet.getString("specificSymbol"),
                resultSet.getString("message")
        );
    }

    @Override
    public TemplateEntity create(TemplateEntity newTemplate) {
        var sql = """ 
                INSERT INTO Template (
                    guid,
                    templateName,
                    receiver,
                    type,
                    amount,
                    currencyId,
                    categoryId,
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
            statement.setString(1, newTemplate.guid());
            statement.setString(2, newTemplate.templateName());
            statement.setString(3, newTemplate.receiver());
            statement.setString(4, newTemplate.transactionType().toString());
            statement.setDouble(5, newTemplate.amount());
            statement.setLong(6, newTemplate.currencyId());
            statement.setLong(7, newTemplate.categoryId());
            statement.setString(8, newTemplate.variableSymbol());
            statement.setString(9, newTemplate.constantSymbol());
            statement.setString(10, newTemplate.specificSymbol());
            statement.setString(11, newTemplate.message());
            statement.executeUpdate();

            try (ResultSet keyResultSet = statement.getGeneratedKeys()) {
                long templateId;

                if (keyResultSet.next()) {
                    templateId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newTemplate);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newTemplate);
                }

                return findById(templateId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newTemplate, ex);
        }
    }

    @Override
    public Collection<TemplateEntity> findAll() {
        var sql = """
                SELECT  
                        id,
                        guid,
                        templateName,
                        receiver,
                        type,
                        amount,
                        currencyId,
                        categoryId,
                        variableSymbol,
                        constantSymbol,
                        specificSymbol,
                        message
                FROM Template
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            List<TemplateEntity> templates = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var template = templateFromResultSet(resultSet);
                    templates.add(template);
                }
            }

            return templates;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all templates", ex);
        }
    }

    @Override
    public Optional<TemplateEntity> findById(long id) {
        var sql = """
                SELECT  id,
                        guid,
                        templateName,
                        receiver,
                        type,
                        amount,
                        currencyId,
                        categoryId,
                        variableSymbol,
                        constantSymbol,
                        specificSymbol,
                        message
                FROM Template
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(templateFromResultSet(resultSet));
            } else {
                // template not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load template by id: " + id, ex);
        }
    }

    @Override
    public Optional<TemplateEntity> findByGuid(String guid) {
        var sql = """
                SELECT  id,
                        guid,
                        templateName,
                        receiver,
                        type,
                        amount,
                        currencyId,
                        categoryId,
                        variableSymbol,
                        constantSymbol,
                        specificSymbol,
                        message
                FROM Template
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(templateFromResultSet(resultSet));
            } else {
                // template not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load template by guid: " + guid, ex);
        }
    }

    @Override
    public TemplateEntity update(TemplateEntity entity) {
        var sql = """
                UPDATE Template
                SET templateName = ?,
                    receiver = ?,
                    type = ?,
                    amount = ?,
                    currencyId = ?,
                    categoryId = ?,
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
            statement.setString(1, entity.templateName());
            statement.setString(2, entity.receiver());
            statement.setString(3, entity.transactionType().toString());
            statement.setDouble(4, entity.amount());
            statement.setLong(5, entity.currencyId());
            statement.setLong(6, entity.categoryId());
            statement.setString(7, entity.variableSymbol());
            statement.setString(8, entity.constantSymbol());
            statement.setString(9, entity.specificSymbol());
            statement.setString(10, entity.message());
            statement.setLong(11, entity.id());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 templates (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update Template: " + entity, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = """
                DELETE FROM Template
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 templates (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete Template, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Template";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all templates", ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM Template
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
            throw new DataStorageException("Failed to check if Template exists, guid: " + guid, ex);
        }
    }
}
