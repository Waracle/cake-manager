package com.philldenness.cakemanager.dto;

import jakarta.validation.constraints.NotNull;

public record CakeRequest(
		@NotNull String title,
		@NotNull String description,
		@NotNull String image
) {
}