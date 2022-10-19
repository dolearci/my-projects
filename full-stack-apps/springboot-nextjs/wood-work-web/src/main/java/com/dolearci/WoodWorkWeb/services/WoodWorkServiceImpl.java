package com.dolearci.WoodWorkWeb.services;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.dolearci.WoodWorkWeb.entities.WoodWork;
import com.dolearci.WoodWorkWeb.models.WoodWorkModel;
import com.dolearci.WoodWorkWeb.repositories.WoodWorkRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WoodWorkServiceImpl implements
		WoodWorkService {
	private final WoodWorkRepository woodWorkRepository;

	public WoodWorkServiceImpl(WoodWorkRepository woodWorkRepository) {
		this.woodWorkRepository = woodWorkRepository;
	}


	@Override
	public List<WoodWorkModel> getAllWoodWork() {

		List<WoodWork> woodWorks = woodWorkRepository.findAll();
		List<WoodWorkModel> woodWorkModels;
		woodWorkModels = woodWorks.stream()
				.map((woodWork -> WoodWorkModel.builder()
						.id(woodWork.getId())
						.type(woodWork.getType())
						.description(woodWork.getDescription())
						.image1(woodWork.getImage1())
						.image2(woodWork.getImage2())
						.image3(woodWork.getImage3())
						.image4(woodWork.getImage4())
						.color(woodWork.getColor())
						.build())).collect(Collectors.toList());
		return woodWorkModels;
	}

	@Override
	public void save(WoodWork woodWork) {
		woodWorkRepository.save(woodWork);
	}

	@Override
	public Optional<WoodWork> getById(Long id) {
		return woodWorkRepository.findById(id);
	}

	@Override
	public void deleteViaId(Long id) {
		woodWorkRepository.deleteById(id);
	}

	@Override
	public WoodWorkModel saveWoodWork(WoodWorkModel woodWorkModel) {

		WoodWork woodWork = new WoodWork();
		BeanUtils.copyProperties(woodWorkModel, woodWork);
		woodWorkRepository.save(woodWork);
		return woodWorkModel;
	}

	@Override
	public WoodWorkModel getWoodWorkById(Long id) {

		WoodWork woodWork = woodWorkRepository.findById(id).get();
		WoodWorkModel woodWorkModel = new WoodWorkModel();
		BeanUtils.copyProperties(woodWork,woodWorkModel);
		return woodWorkModel;
	}

	@Override
	public WoodWorkModel updateUser(Long id, WoodWorkModel woodWorkModel) {
		WoodWork woodWork = woodWorkRepository.findById(id).get();
		woodWork.setDescription(woodWorkModel.getDescription());
		woodWork.setType(woodWorkModel.getType());
		woodWork.setImage1(woodWorkModel.getImage1());
		woodWork.setImage2(woodWorkModel.getImage2());
		woodWork.setImage3(woodWorkModel.getImage3());
		woodWork.setImage4(woodWorkModel.getImage4());
		woodWork.setColor(woodWorkModel.getColor());
		woodWorkRepository.save(woodWork);
		return woodWorkModel;
	}

	@Override
	public boolean deleteWoodWork(Long id) {

		WoodWork woodWork = woodWorkRepository.findById(id).get();
		woodWorkRepository.delete(woodWork);
		return true;
	}
}
