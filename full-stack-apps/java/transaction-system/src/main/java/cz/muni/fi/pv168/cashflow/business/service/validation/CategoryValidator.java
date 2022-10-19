package cz.muni.fi.pv168.cashflow.business.service.validation;

import cz.muni.fi.pv168.cashflow.business.model.Category;
import cz.muni.fi.pv168.cashflow.business.service.validation.common.StringLengthValidator;

import java.util.List;

import static cz.muni.fi.pv168.cashflow.business.service.validation.Validator.extracting;

/**
 * Category entity {@link Validator}
 */
public class CategoryValidator implements Validator<Category> {

    @Override
    public ValidationResult validate(Category department) {
        var validators = List.of(
                extracting(Category::getName, new StringLengthValidator(1, 20, "Category name"))
        );

        return Validator.compose(validators).validate(department);
    }
}
