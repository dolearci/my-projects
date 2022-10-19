package cz.muni.fi.pv168.cashflow.business.service.validation.common;

import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;

public final class NonZeroNumberValidator extends PropertyValidator<Double> {

    public NonZeroNumberValidator() {
        this(null);
    }

    public NonZeroNumberValidator(String name) {
        super(name);
    }

    @Override
    public ValidationResult validate(Double number) {
        var result = new ValidationResult();

        if (number == 0) {
            result.add("Amount cannot be zero");
        }

        return result;
    }
}
