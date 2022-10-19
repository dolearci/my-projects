package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.repository.CurrencyRepository;
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

class CurrencyCrudServiceTest {

    private CurrencyRepository currencyRepository = Mockito.mock(CurrencyRepository.class);
    private Validator<Currency> currencyValidator = Mockito.mock(Validator.class);
    private GuidProvider guidProvider = Mockito.mock(GuidProvider.class);
    private CurrencyCrudService currencyCrudService = new CurrencyCrudService(currencyRepository, currencyValidator, guidProvider);

    @Test
    void findAllCurrencies() {
        List<Currency> expectedCurrencies = Arrays.asList(new Currency("guid1", "USD", "Dollar", 1), new Currency("guid2", "EUR", "Euro", 1));
        when(currencyRepository.findAll()).thenReturn(expectedCurrencies);

        List<Currency> actualCurrencies = currencyCrudService.findAll();

        assertThat(actualCurrencies).containsExactlyElementsOf(expectedCurrencies);
    }

    @Test
    void createValidCurrency() {
        Currency validCurrency = new Currency("guid", "USD", "Dollar", 1);
        ValidationResult mockResult = new ValidationResult();
        when(currencyValidator.validate(validCurrency)).thenReturn(mockResult);

        ValidationResult result = currencyCrudService.create(validCurrency);

        verify(currencyRepository).create(validCurrency);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void createCurrencyWithExistingGuid() {
        Currency currencyWithExistingGuid = new Currency("existingGuid", "USD", "Dollar", 1);
        when(currencyRepository.existsByGuid("existingGuid")).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> currencyCrudService.create(currencyWithExistingGuid));
    }

    @Test
    void createCurrencyWithoutGuid() {
        Currency currencyWithoutGuid = new Currency(null, "USD", "Dollar", 1);
        ValidationResult mockResult = new ValidationResult();
        when(currencyValidator.validate(currencyWithoutGuid)).thenReturn(mockResult);
        when(guidProvider.newGuid()).thenReturn("newGeneratedGuid");

        ValidationResult result = currencyCrudService.create(currencyWithoutGuid);

        assertThat(currencyWithoutGuid.getGuid()).isEqualTo("newGeneratedGuid");
        verify(currencyRepository).create(currencyWithoutGuid);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void updateValidCurrency() {
        Currency validCurrency = new Currency("guid", "USD", "Dollar", 1);
        ValidationResult mockResult = new ValidationResult();
        when(currencyValidator.validate(validCurrency)).thenReturn(mockResult);

        ValidationResult result = currencyCrudService.update(validCurrency);

        verify(currencyRepository).update(validCurrency);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void deleteByGuid() {
        String guid = "test-guid";

        currencyCrudService.deleteByGuid(guid);

        verify(currencyRepository).deleteByGuid(guid);
    }

    @Test
    void deleteAll() {
        currencyCrudService.deleteAll();

        verify(currencyRepository).deleteAll();
    }
}