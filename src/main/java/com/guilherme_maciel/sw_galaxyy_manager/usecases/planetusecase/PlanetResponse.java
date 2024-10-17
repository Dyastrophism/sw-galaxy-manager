package com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase;

import com.guilherme_maciel.sw_galaxyy_manager.entity.Planet;

public record PlanetResponse(
        Long id,
        String name,
        String climate,
        String terrain,
        Long population
) {

    public static PlanetResponse fromEntity(Planet planet) {
        return new PlanetResponse(
                planet.getId(),
                planet.getName(),
                planet.getClimate(),
                planet.getTerrain(),
                planet.getPopulation());
    }
}
