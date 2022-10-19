package cz.muni.fi.pv168.cashflow.business.service.validation;

import cz.muni.fi.pv168.cashflow.business.model.Currency;
import cz.muni.fi.pv168.cashflow.business.service.validation.common.StringLengthValidator;

import java.util.List;

import static cz.muni.fi.pv168.cashflow.business.service.validation.Validator.extracting;

/**
 * Currency entity {@link Validator}
 */
public class CurrencyValidator implements Validator<Currency> {

    @Override
    public ValidationResult validate(Currency department) {
        var validators = List.of(
                extracting(Currency::getName, new StringLengthValidator(1, 20, "Currency name")),
                extracting(Currency::getCode, new StringLengthValidator(3, 3, "Currency code"))
        );

        return Validator.compose(validators).validate(department);
    }
}
