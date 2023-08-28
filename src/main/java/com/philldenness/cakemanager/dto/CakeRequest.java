package com.philldenness.cakemanager.dto;

import jakarta.validation.constraints.NotBlank;

public record CakeRequest(
		@NotBlank String title,
		@NotBlank String description,
		@NotBlank String image
) {
}