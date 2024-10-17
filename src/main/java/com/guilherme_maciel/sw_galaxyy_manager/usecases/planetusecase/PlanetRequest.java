package com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlanetRequest(
        @NotBlank(message = "Name is mandatory")
        String name,
        @NotBlank(message = "Climate is mandatory")
        String climate,
        @NotBlank(message = "Terrain is mandatory")
        String terrain,
        @NotNull(message = "Population is mandatory")
        @Positive(message = "Population must be positive")
        Long population
) {
}
