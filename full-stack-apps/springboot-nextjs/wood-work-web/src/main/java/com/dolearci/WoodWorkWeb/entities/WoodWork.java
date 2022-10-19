package com.dolearci.WoodWorkWeb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wood_work")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WoodWork {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	private String type;

	@Column(name = "image1",length = 1000000000)
	private String image1;
	@Column(name = "image2",length = 1000000000)
	private String image2;
	@Column(name = "image3",length = 1000000000)
	private String image3;
	@Column(name = "image4",length = 1000000000)
	private String image4;

	@Column(name = "color")
	private String color;
}
