package cz.muni.fi.pv168.cashflow.storage.sql;

import cz.muni.fi.pv168.cashflow.business.model.Template;
import cz.muni.fi.pv168.cashflow.business.repository.TemplateRepository;
import cz.muni.fi.pv168.cashflow.storage.DataStorageException;
import cz.muni.fi.pv168.cashflow.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.TemplateEntity;
import cz.muni.fi.pv168.cashflow.storage.sql.entity.mapper.EntityMapper;

import java.util.List;

public class TemplateSqlRepository implements TemplateRepository {

    private final DataAccessObject<TemplateEntity> templateDao;
    private final EntityMapper<TemplateEntity, Template> templateMapper;

    public TemplateSqlRepository(
            DataAccessObject<TemplateEntity> templateDao,
            EntityMapper<TemplateEntity, Template> templateMapper) {
        this.templateDao = templateDao;
        this.templateMapper = templateMapper;
    }

    @Override
    public List<Template> findAll() {
        return templateDao.findAll().stream().map(templateMapper::mapToBusiness).toList();
    }

    @Override
    public void create(Template newEntity) {
        templateDao.create(templateMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Template entity) {
        var existingTemplate = templateDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Department not found, guid: " + entity.getGuid()));
        var updatedTemplate = templateMapper.mapExistingEntityToDatabase(entity, existingTemplate.id());

        templateDao.update(updatedTemplate);

    }

    @Override
    public void deleteByGuid(String guid) {
        templateDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        templateDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return templateDao.existsByGuid(guid);
    }

}
