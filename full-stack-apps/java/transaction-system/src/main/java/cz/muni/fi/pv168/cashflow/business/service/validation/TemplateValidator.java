package cz.muni.fi.pv168.cashflow.business.service.validation;

import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.service.validation.common.NonZeroNumberValidator;
import cz.muni.fi.pv168.cashflow.business.service.validation.common.StringLengthValidator;

import java.util.List;

import static cz.muni.fi.pv168.cashflow.business.service.validation.Validator.extracting;

/**
 * Template entity {@link Validator}
 */
public class TemplateValidator implements Validator<Template> {

    @Override
    public ValidationResult validate(Template template) {
        var validators = List.of(
                extracting(Template::getReceiver, new StringLengthValidator(1, 20, "Receiver name")),
                extracting(Template::getTemplateName, new StringLengthValidator(1, 20, "Template name")),
                extracting(Template::getAmount, new NonZeroNumberValidator("Amount")),
                extracting(Template::getVariableSymbol, new StringLengthValidator(0, 10, "Variable symbol")),
                extracting(Template::getConstantSymbol, new StringLengthValidator(0, 10, "Constant symbol")),
                extracting(Template::getSpecificSymbol, new StringLengthValidator(0, 10, "Specific symbol"))
        );

        return Validator.compose(validators).validate(template);
    }
}