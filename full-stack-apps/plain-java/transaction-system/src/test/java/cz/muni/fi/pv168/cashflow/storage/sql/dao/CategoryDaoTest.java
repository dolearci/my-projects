package cz.muni.fi.pv168.cashflow.storage.sql.dao;

import cz.muni.fi.pv168.cashflow.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CategoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryDaoTest {

    private ConnectionHandler connectionHandler;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private CategoryDao categoryDao;

    @BeforeEach
    void setUp() throws SQLException {
        connectionHandler = Mockito.mock(ConnectionHandler.class);
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        categoryDao = new CategoryDao(() -> connectionHandler);

        when(connectionHandler.use()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
    }

    @Test
    void create() throws SQLException {
        CategoryEntity newCategory = new CategoryEntity(1L, "guid", "name");
        when(resultSet.next()).thenReturn(true, false, true);
        when(resultSet.getLong(1)).thenReturn(1L);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("guid")).thenReturn("guid");
        when(resultSet.getString("name")).thenReturn("name");

        CategoryEntity createdCategory = categoryDao.create(newCategory);

        verify(statement).setString(1, newCategory.guid());
        verify(statement).setString(2, newCategory.category());
        assertThat(createdCategory).isEqualTo(newCategory);
    }

    @Test
    void findAll() throws SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("guid")).thenReturn("guid");
        when(resultSet.getString("name")).thenReturn("name");

        var categories = categoryDao.findAll();

        assertThat(categories).hasSize(1);
        assertThat(categories.iterator().next()).isEqualTo(new CategoryEntity(1L, "guid", "name"));
    }

    @Test
    void findById() throws SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("guid")).thenReturn("guid");
        when(resultSet.getString("name")).thenReturn("name");

        Optional<CategoryEntity> category = categoryDao.findById(1L);

        verify(statement).setLong(1, 1L);
        assertThat(category).isPresent();
        assertThat(category.get()).isEqualTo(new CategoryEntity(1L, "guid", "name"));
    }

    @Test
    public void findByGuid() throws SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("guid")).thenReturn("guid");
        when(resultSet.getString("name")).thenReturn("name");

        Optional<CategoryEntity> category = categoryDao.findByGuid("guid");

        verify(statement).setString(1, "guid");
        assertThat(category).isPresent();
        assertThat(category.get()).isEqualTo(new CategoryEntity(1L, "guid", "name"));
    }

    @Test
    public void update() throws SQLException {
        CategoryEntity entity = new CategoryEntity(1L, "test-guid", "test-name");
        when(statement.executeUpdate()).thenReturn(1);

        CategoryEntity result = categoryDao.update(entity);

        assertThat(result).isEqualTo(entity);
        verify(statement).setString(1, entity.category());
        verify(statement).setLong(2, entity.id());
    }

    @Test
    public void deleteByGuid() throws SQLException {
        when(statement.executeUpdate()).thenReturn(1);

        categoryDao.deleteByGuid("guid");

        verify(statement).setString(1, "guid");
    }

    @Test
    public void deleteAll() throws SQLException {
        when(statement.executeUpdate()).thenReturn(1);

        categoryDao.deleteAll();

        verify(statement).executeUpdate();
    }

    @Test
    public void existsByGuid() throws SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        boolean result = categoryDao.existsByGuid("guid");

        assertThat(result).isTrue();
        verify(statement).setString(1, "guid");
    }

    @Test
    public void existsByName() throws SQLException {
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        boolean result = categoryDao.existsByName("name");

        assertThat(result).isTrue();
        verify(statement).setString(1, "name");
    }
}