package com.dolearci.WoodWorkWeb.services;

import com.dolearci.WoodWorkWeb.entities.WoodWork;
import com.dolearci.WoodWorkWeb.models.WoodWorkModel;

import java.util.List;
import java.util.Optional;

public interface WoodWorkService {

	List<WoodWorkModel> getAllWoodWork();

	void save(WoodWork woodWork);

	Optional<WoodWork> getById(Long id);

	void deleteViaId(Long id);

	boolean deleteWoodWork(Long id);

	WoodWorkModel saveWoodWork(WoodWorkModel woodWorkModel);

	WoodWorkModel getWoodWorkById(Long id);

	WoodWorkModel updateUser(Long id, WoodWorkModel woodWorkModel);
}
