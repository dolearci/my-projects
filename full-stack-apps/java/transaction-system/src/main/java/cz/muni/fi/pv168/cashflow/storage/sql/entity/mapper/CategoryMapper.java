package cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.CategoryEntity;

public class CategoryMapper implements EntityMapper<CategoryEntity, Category> {

    @Override
    public Category mapToBusiness(CategoryEntity entity) {
        return new Category(
                entity.guid(),
                entity.category());
    }

    @Override
    public CategoryEntity mapNewEntityToDatabase(Category entity) {
        return new CategoryEntity(
                entity.getGuid(),
                entity.getName());
    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category entity, Long dbId) {
        return new CategoryEntity(
                dbId,
                entity.getGuid(),
                entity.getName());
    }
}
