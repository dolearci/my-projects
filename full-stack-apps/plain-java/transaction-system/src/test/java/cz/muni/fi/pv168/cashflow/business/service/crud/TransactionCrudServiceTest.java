package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.model.TransactionType;
import cz.muni.fi.pv168.cashflow.business.repository.TransactionRepository;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionCrudServiceTest {

    private TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private Validator<Transaction> transactionValidator = Mockito.mock(Validator.class);
    private GuidProvider guidProvider = Mockito.mock(GuidProvider.class);
    private TransactionCrudService transactionCrudService = new TransactionCrudService(transactionRepository, transactionValidator, guidProvider);

    @Test
    void findAllTransactions() {
        Transaction transaction1 = new Transaction(
                "guid1",
                "Receiver1",
                TransactionType.OUTCOME,
                100.0,
                new Currency("guidCurrency1", "USD", "Dollar", 1),
                new Category("guidCategory1", "Category1"),
                LocalDate.of(2023, 1, 1),
                "12345",
                "54321",
                "67890",
                "Payment for services"
        );

        Transaction transaction2 = new Transaction(
                "guid2",
                "Receiver2",
                TransactionType.INCOME,
                200.0,
                new Currency("guidCurrency2", "EUR", "Euro", 1),
                new Category("guidCategory2", "Category2"),
                LocalDate.of(2023, 2, 1),
                "22345",
                "24321",
                "27890",
                "Invoice payment"
        );
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findAll()).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionCrudService.findAll();

        assertThat(actualTransactions).containsExactlyElementsOf(expectedTransactions);
    }

    @Test
    void createValidTransaction() {
        Transaction validTransaction = new Transaction(
                "guid1",
                "Receiver1",
                TransactionType.OUTCOME,
                100.0,
                new Currency("guidCurrency1", "USD", "Dollar", 1),
                new Category("guidCategory1", "Category1"),
                LocalDate.of(2023, 1, 1),
                "12345",
                "54321",
                "67890",
                "Payment for services"
        );

        ValidationResult mockResult = new ValidationResult();
        when(transactionValidator.validate(validTransaction)).thenReturn(mockResult);

        ValidationResult result = transactionCrudService.create(validTransaction);

        verify(transactionRepository).create(validTransaction);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void updateValidTransaction() {
        Transaction validTransaction = new Transaction(
                "guid1",
                "Receiver1",
                TransactionType.OUTCOME,
                100.0,
                new Currency("guidCurrency1", "USD", "Dollar", 1),
                new Category("guidCategory 1", "Category1"),
                LocalDate.of(2023, 1, 1),
                "12345",
                "54321",
                "67890",
                "Payment for services"
        );

        ValidationResult mockResult = new ValidationResult();
        when(transactionValidator.validate(validTransaction)).thenReturn(mockResult);

        ValidationResult result = transactionCrudService.update(validTransaction);

        verify(transactionRepository).update(validTransaction);

        assertThat(result).isEqualTo(mockResult);
    }

    @Test
    void deleteByGuid() {
        String guid = "test-guid";

        transactionCrudService.deleteByGuid(guid);

        verify(transactionRepository).deleteByGuid(guid);
    }

    @Test
    void deleteAll() {
        transactionCrudService.deleteAll();

        verify(transactionRepository).deleteAll();
    }
}