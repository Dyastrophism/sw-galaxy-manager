package com.guilherme_maciel.sw_galaxyy_manager.service;

import com.guilherme_maciel.sw_galaxyy_manager.entity.Planet;
import com.guilherme_maciel.sw_galaxyy_manager.exception.PlanetNotFoundException;
import com.guilherme_maciel.sw_galaxyy_manager.repository.PlanetRepository;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetRequest;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetResponse;
import com.guilherme_maciel.sw_galaxyy_manager.util.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlanetService {

    private static final Logger logger = LoggerFactory.getLogger(PlanetService.class);
    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    /**
     * Create a new planet
     * @param planetRequest the planet request
     * @return the created planet
     */
    public Mono<PlanetResponse> createPlanet(PlanetRequest planetRequest) {
        logger.info("Creating new planet with name: {}", planetRequest.name());
        Planet newPlanet = new Planet().fromRequest(planetRequest);
        return planetRepository.save(newPlanet)
                .map(PlanetResponse::fromEntity)
                .doOnSuccess(response -> logger.info("Created planet with id: {}", response.id()))
                .doOnError(error -> logger.error("Error creating planet", error));
    }

    /**
     * List a planet by id
     * @param id the planet id
     * @return the planet
     */
    public Mono<PlanetResponse> listPlanetById(Long id) {
        logger.info("Listing planet by id: {}", id);
        return planetRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlanetNotFoundException("Planet not found with id: " + id)))
                .map(PlanetResponse::fromEntity)
                .doOnError(error -> logger.error("Error listing planet by id: {}", id, error));
    }

    /**
     * List all planets
     * @param name the planet name
     * @return the planets
     */
    public Flux<PlanetResponse> listAllPlanets(String name) {
        logger.info("Listing all planets with name: {}", name);
        var planet = new Planet(null, name, null, null, null);
        Example<Planet> query = QueryBuilder.makeQuery(planet);
        return planetRepository.findAll(query, Sort.by("name").ascending())
                .map(PlanetResponse::fromEntity)
                .doOnError(error -> logger.error("Error listing all planets", error));
    }

    /**
     * Update a planet by id
     * @param id the planet id
     * @param planetRequest the planet request
     * @return the updated planet
     */
    public Mono<PlanetResponse> updatePlanetById(Long id, PlanetRequest planetRequest) {
        logger.info("Updating planet with id: {}", id);
        return planetRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlanetNotFoundException("Planet not found with id: " + id)))
                .flatMap(existingPlanet -> updatePlanetFields(existingPlanet, planetRequest))
                .flatMap(planetRepository::save)
                .map(PlanetResponse::fromEntity)
                .doOnSuccess(response -> logger.info("Updated planet with id: {}", response.id()))
                .doOnError(error -> logger.error("Error updating planet with id: {}", id, error));
    }

    /**
     * Delete a planet by id
     * @param id the planet id
     * @return void
     */
    public Mono<Void> deletePlanetById(Long id) {
        logger.info("Deleting planet with id: {}", id);
        return planetRepository.findById(id)
                .switchIfEmpty(Mono.error(new PlanetNotFoundException("Planet not found with id: " + id)))
                .flatMap(existingPlanet -> planetRepository.deleteById(id))
                .doOnSuccess(unused -> logger.info("Deleted planet with id: {}", id))
                .doOnError(error -> logger.error("Error deleting planet with id: {}", id, error));
    }

    private Mono<Planet> updatePlanetFields(Planet planet, PlanetRequest planetRequest) {
        planet.setName(planetRequest.name());
        planet.setClimate(planetRequest.climate());
        planet.setTerrain(planetRequest.terrain());
        planet.setPopulation(planetRequest.population());
        return Mono.just(planet);
    }
}