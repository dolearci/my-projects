package cz.muni.fi.pv168.cashflow.business.service.validation;

import cz.muni.fi.pv168.cashflow.business.model.Transaction;
import cz.muni.fi.pv168.cashflow.business.service.validation.common.NonZeroNumberValidator;
import cz.muni.fi.pv168.cashflow.business.service.validation.common.StringLengthValidator;

import java.util.List;

import static cz.muni.fi.pv168.cashflow.business.service.validation.Validator.extracting;

/**
 * Transaction entity {@link Validator}
 */
public class TransactionValidator implements Validator<Transaction> {

    @Override
    public ValidationResult validate(Transaction department) {
        var validators = List.of(
                extracting(Transaction::getReceiver, new StringLengthValidator(1, 20, "Receiver name")),
                extracting(Transaction::getAmount, new NonZeroNumberValidator("Amount")),
                extracting(Transaction::getVariableSymbol, new StringLengthValidator(0, 10, "Variable symbol")),
                extracting(Transaction::getConstantSymbol, new StringLengthValidator(0, 10, "Constant symbol")),
                extracting(Transaction::getSpecificSymbol, new StringLengthValidator(0, 10, "Specific symbol"))
        );

        return Validator.compose(validators).validate(department);
    }
}
