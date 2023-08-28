package com.philldenness.cakemanager.dto;

public record CakeDTO(
		Long id,
		String title,
		String description,
		String image
) {
}