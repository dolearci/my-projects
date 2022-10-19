package cz.muni.fi.pv168.cashflow.business.service.crud;

import cz.muni.fi.pv168.cashflow.business.model.GuidProvider;
import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.repository.TemplateRepository;
import cz.muni.fi.pv168.cashflow.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.cashflow.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Template} entity.
 */
public class TemplateCrudService implements CrudService<Template> {

    private final TemplateRepository templateRepository;
    private final Validator<Template> templateValidator;
    private final GuidProvider guidProvider;

    public TemplateCrudService(TemplateRepository templateRepository,
                               Validator<Template> templateValidator,
                               GuidProvider guidProvider) {
        this.templateRepository = templateRepository;
        this.templateValidator = templateValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Template> findAll() {
        return templateRepository.findAll();
    }

    @Override
    public ValidationResult create(Template newEntity) {
        var validationResult = templateValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (templateRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Template with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            templateRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Template entity) {
        var validationResult = templateValidator.validate(entity);
        if (validationResult.isValid()) {
            templateRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        templateRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        templateRepository.deleteAll();
    }
}
