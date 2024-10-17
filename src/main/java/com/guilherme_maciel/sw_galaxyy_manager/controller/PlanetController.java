package com.guilherme_maciel.sw_galaxyy_manager.controller;

import com.guilherme_maciel.sw_galaxyy_manager.service.PlanetService;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetRequest;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<Mono<PlanetResponse>> createPlanet(@RequestBody PlanetRequest planet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.createPlanet(planet));
    }

    @GetMapping
    public ResponseEntity<Flux<PlanetResponse>> listAllPlanets(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(planetService.listAllPlanets(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<PlanetResponse>> listPlanetById(@PathVariable Long id) {
        return ResponseEntity.ok(planetService.listPlanetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<PlanetResponse>> updatePlanetById(@PathVariable Long id, @RequestBody PlanetRequest planet) {
        return ResponseEntity.ok(planetService.updatePlanetById(id, planet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deletePlanetById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(planetService.deletePlanetById(id));
    }
}
