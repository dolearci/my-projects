package cz.muni.fi.pv168.cashflow.storage.sql;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.repository.CategoryRepository;
import cz.muni.fi.pv168.cashflow.storage.DataStorageException;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.CategoryDao;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.EntityMapper;

import java.util.List;

public class CategorySqlRepository implements CategoryRepository {

    private final CategoryDao categoryDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;

    public CategorySqlRepository(
            CategoryDao categoryDao,
            EntityMapper<CategoryEntity, Category> categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll().stream().map(categoryMapper::mapToBusiness).toList();
    }

    @Override
    public void create(Category newEntity) {
        categoryDao.create(categoryMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Category entity) {
        var existingCategory = categoryDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Department not found, guid: " + entity.getGuid()));
        var updatedCategory = categoryMapper.mapExistingEntityToDatabase(entity, existingCategory.id());

        categoryDao.update(updatedCategory);

    }

    @Override
    public void deleteByGuid(String guid) {
        categoryDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        categoryDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return categoryDao.existsByGuid(guid);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryDao.existsByName(name);
    }
}