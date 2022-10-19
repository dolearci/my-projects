package com.dolearci.WoodWorkWeb.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dolearci.WoodWorkWeb.models.WoodWorkModel;
import com.dolearci.WoodWorkWeb.services.WoodWorkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value ="http://localhost:3000")
@RequestMapping("/woodwork")
public class WoodWorkController {

	private final WoodWorkService woodWorkService;

	public WoodWorkController(WoodWorkService woodWorkService) {
		this.woodWorkService = woodWorkService;
	}

	@GetMapping
	public List<WoodWorkModel> getAllWoodWorks()
	{
		return woodWorkService.getAllWoodWork();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteWoodWork(@PathVariable("id") Long id) {
		boolean deleted = false;
		deleted = woodWorkService.deleteWoodWork(id);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", deleted);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public WoodWorkModel saveWoodWork(@RequestBody WoodWorkModel woodWorkModel) {
		return woodWorkService.saveWoodWork(woodWorkModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<WoodWorkModel> getWoodWorkById(@PathVariable("id") Long id) {
		WoodWorkModel woodWorkModel = null;
		woodWorkModel = woodWorkService.getWoodWorkById(id);
		return ResponseEntity.ok(woodWorkModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<WoodWorkModel> updateWoodWork(@PathVariable("id") Long id,
			@RequestBody WoodWorkModel woodWorkModel) {
		woodWorkModel = woodWorkService.updateUser(id,woodWorkModel);
		return ResponseEntity.ok(woodWorkModel);
	}
}
