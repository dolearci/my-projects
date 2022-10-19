package com.dolearci.WoodWorkWeb.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WoodWorkModel {
	private Long id;
	private String description;
	private String type;
	private String image1;
	private String image2;
	private String image3;
	private String image4;
	private String color;
}
