package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.business.repository.TemplateRepository;
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

class TemplateCrudServiceTest {

    private TemplateRepository templateRepository = Mockito.mock(TemplateRepository.class);
    private Validator<Template> templateValidator = Mockito.mock(Validator.class);
    private GuidProvider guidProvider = Mockito.mock(GuidProvider.class);
    private TemplateCrudService templateCrudService = new TemplateCrudService(templateRepository, templateValidator, guidProvider);

    @Test
    void findAllTemplates() {
        Template template1 = new Template(
                "guid1",
                "TemplateName1",
                "Receiver1",
                TransactionType.OUTCOME,
                100.0,
                new Currency("guidCurrency1", "USD", "Dollar", 1),
                new Category("guidCategory1", "Category1"),
                "12345",
                "54321",
                "67890",
                "Message1"
        );

        Template template2 = new Template(
                "guid2",
                "TemplateName2",
                "Receiver2",
                TransactionType.INCOME,
                200.0,
                new Currency("guidCurrency2", "EUR", "Euro", 1),
                new Category("guidCategory2", "Category2"),
                "22345",
                "24321",
                "27890",
                "Message2"
        );

        List<Template> expectedTemplates = Arrays.asList(template1, template2);
        when(templateRepository.findAll()).thenReturn(expectedTemplates);

        List<Template> actualTemplates = templateCrudService.findAll();

        assertThat(actualTemplates).containsExactlyInAnyOrderElementsOf(expectedTemplates);
    }

    @Test
    void createValidTemplate() {
        Template validTemplate = new Template(
                "guid1",
                "TemplateName1",
                "Receiver1",
                TransactionType.OUTCOME,
                100.0,
                new Currency("guidCurrency1", "USD", "Dollar", 1),
                new Category("guidCategory1", "Category1"),
                "12345",
                "54321",
                "67890",
                "Message1"
        );

        ValidationResult mockResult = new ValidationResult();
        when(templateValidator.validate(validTemplate)).thenReturn(mockResult);

        ValidationResult result = templateCrudService.create(validTemplate);

        verify(templateRepository).create(validTemplate);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void createTemplateWithExistingGuid() {
        Template templateWithExistingGuid = new Template(
                "existingGuid",
                "TemplateName2",
                "Receiver2",
                TransactionType.INCOME,
                200.0,
                new Currency("guidCurrency2", "EUR", "Euro", 1),
                new Category("guidCategory2", "Category2"),
                "22345",
                "24321",
                "27890",
                "Message2"
        );
        when(templateRepository.existsByGuid("existingGuid")).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> templateCrudService.create(templateWithExistingGuid));
    }

    @Test
    void updateValidTemplate() {
        Template validTemplate = new Template(
                "guid1",
                "TemplateName1",
                "Receiver1",
                TransactionType.OUTCOME,
                100.0,
                new Currency("guidCurrency1", "USD", "Dollar", 1),
                new Category("guidCategory1", "Category1"),
                "12345",
                "54321",
                "67890",
                "Message1"
        );

        ValidationResult mockResult = new ValidationResult();
        when(templateValidator.validate(validTemplate)).thenReturn(mockResult);

        ValidationResult result = templateCrudService.update(validTemplate);

        verify(templateRepository).update(validTemplate);
        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void deleteByGuid() {
        String guid = "test-guid";

        templateCrudService.deleteByGuid(guid);

        verify(templateRepository).deleteByGuid(guid);
    }

    @Test
    void deleteAll() {
        templateCrudService.deleteAll();

        verify(templateRepository).deleteAll();
    }
}