package com.guilherme_maciel.sw_galaxyy_manager.common;

import com.guilherme_maciel.sw_galaxyy_manager.entity.Planet;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetRequest;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetResponse;

public class PlanetCommons {

    public static final PlanetRequest PLANET_REQUEST = new PlanetRequest("Tatooine", "arid", "desert", 200000L);
    public static final Planet PLANET = new Planet(1L, "Tatooine", "arid", "desert", 200000L);
    public static final PlanetResponse PLANET_RESPONSE = new PlanetResponse(1L, "Tatooine", "arid", "desert", 200000L);

    public static final PlanetRequest UPDATED_PLANET_REQUEST = new PlanetRequest("Tatooine", "arid", "desert", 200000L);
    public static final Planet UPDATED_PLANET = new Planet(1L, "Mars", "arid", "desert", 200000L);
    public static final PlanetResponse UPDATED_PLANET_RESPONSE = PlanetResponse.fromEntity(UPDATED_PLANET);
}
