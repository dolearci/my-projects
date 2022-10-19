package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.repository.CategoryRepository;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Category} entity.
 */
public class CategoryCrudService implements CrudService<Category> {

    private final CategoryRepository categoryRepository;
    private final Validator<Category> categoryValidator;
    private final GuidProvider guidProvider;

    public CategoryCrudService(CategoryRepository categoryRepository,
                               Validator<Category> categoryValidator,
                               GuidProvider guidProvider) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ValidationResult create(Category newEntity) {
        var validationResult = categoryValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (categoryRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Category with given guid already exists: " + newEntity.getGuid());
        }

        if (categoryRepository.existsByName(newEntity.getName())) {
            throw new EntityAlreadyExistsException("Category with given name but different code already exists: " + newEntity.getName());
        }

        if (validationResult.isValid()) {
            categoryRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Category entity) {
        var validationResult = categoryValidator.validate(entity);
        if (validationResult.isValid()) {
            categoryRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        categoryRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
