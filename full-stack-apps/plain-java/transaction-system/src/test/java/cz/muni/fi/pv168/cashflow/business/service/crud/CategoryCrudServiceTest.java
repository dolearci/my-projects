package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.repository.CategoryRepository;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryCrudServiceTest {

    private CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    private Validator<Category> categoryValidator = Mockito.mock(Validator.class);
    private GuidProvider guidProvider = Mockito.mock(GuidProvider.class);
    private CategoryCrudService categoryCrudService = new CategoryCrudService(categoryRepository, categoryValidator, guidProvider);

    @Test
    void updateValidCategory() {
        Category validCategory = new Category("guid", "name");
        ValidationResult mockResult = new ValidationResult();
        when(categoryValidator.validate(validCategory)).thenReturn(mockResult);

        ValidationResult result = categoryCrudService.update(validCategory);

        verify(categoryRepository).update(validCategory);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void findAllCategories() {
        List<Category> expectedCategories = Arrays.asList(new Category("guid1", "name1"), new Category("guid2", "name2"));
        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        List<Category> actualCategories = categoryCrudService.findAll();

        assertThat(actualCategories).containsExactlyElementsOf(expectedCategories);
    }

    @Test
    void createValidCategory() {
        Category validCategory = new Category("guid", "name");
        ValidationResult mockResult = new ValidationResult();
        when(categoryValidator.validate(validCategory)).thenReturn(mockResult);

        ValidationResult result = categoryCrudService.create(validCategory);

        verify(categoryRepository).create(validCategory);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void createCategoryWithExistingGuid() {
        Category categoryWithExistingGuid = new Category("existingGuid", "name");
        when(categoryRepository.existsByGuid("existingGuid")).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> categoryCrudService.create(categoryWithExistingGuid));
    }

    @Test
    void createCategoryWithExistingName() {
        Category categoryWithExistingName = new Category("newGuid", "existingName");
        when(categoryRepository.existsByName("existingName")).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> categoryCrudService.create(categoryWithExistingName));
    }

    @Test
    void deleteByGuid() {
        String guid = "test-guid";

        categoryCrudService.deleteByGuid(guid);

        verify(categoryRepository).deleteByGuid(guid);
    }

    @Test
    void deleteAll() {
        categoryCrudService.deleteAll();

        verify(categoryRepository).deleteAll();
    }
}