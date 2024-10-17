package com.guilherme_maciel.sw_galaxyy_manager.controller;

import com.guilherme_maciel.sw_galaxyy_manager.service.PlanetService;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetRequest;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static com.guilherme_maciel.sw_galaxyy_manager.common.PlanetCommons.PLANET_RESPONSE;
import static com.guilherme_maciel.sw_galaxyy_manager.common.PlanetCommons.PLANET_REQUEST;
import static com.guilherme_maciel.sw_galaxyy_manager.common.PlanetCommons.UPDATED_PLANET_REQUEST;
import static com.guilherme_maciel.sw_galaxyy_manager.common.PlanetCommons.UPDATED_PLANET_RESPONSE;

public class PlanetControllerTest {

    @Mock
    private PlanetService planetService;

    @InjectMocks
    private PlanetController planetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlanet_WithValidPlanetRequest_ReturnCreatedPlanet() {
        when(planetService.createPlanet(any(PlanetRequest.class))).thenReturn(Mono.just(PLANET_RESPONSE));

        ResponseEntity<Mono<PlanetResponse>> result = planetController.createPlanet(PLANET_REQUEST);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        StepVerifier.create(result.getBody())
                .expectNext(PLANET_RESPONSE)
                .verifyComplete();
    }

    @Test
    void listAllPlanets_WithValidName_ReturnPlanets() {
        when(planetService.listAllPlanets(any(String.class))).thenReturn(Flux.just(PLANET_RESPONSE));

        ResponseEntity<Flux<PlanetResponse>> result = planetController.listAllPlanets("Tatooine");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        StepVerifier.create(result.getBody())
                .expectNext(PLANET_RESPONSE)
                .verifyComplete();
    }

    @Test
    void listPlanetById_WithValidId_ReturnPlanet() {
        when(planetService.listPlanetById(anyLong())).thenReturn(Mono.just(PLANET_RESPONSE));

        ResponseEntity<Mono<PlanetResponse>> result = planetController.listPlanetById(PLANET_RESPONSE.id());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        StepVerifier.create(result.getBody())
                .expectNext(PLANET_RESPONSE)
                .verifyComplete();
    }

    @Test
    void updatePlanetById_WithValidId_ReturnUpdatedPlanet() {
        when(planetService.updatePlanetById(anyLong(), any(PlanetRequest.class))).thenReturn(Mono.just(UPDATED_PLANET_RESPONSE));

        ResponseEntity<Mono<PlanetResponse>> result = planetController.updatePlanetById(UPDATED_PLANET_RESPONSE.id(), UPDATED_PLANET_REQUEST);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        StepVerifier.create(result.getBody())
                .expectNext(UPDATED_PLANET_RESPONSE)
                .verifyComplete();
    }

    @Test
    void deletePlanetById_WithValidId_ReturnNoContent() {
        when(planetService.deletePlanetById(anyLong())).thenReturn(Mono.empty());

        ResponseEntity<Mono<Void>> result = planetController.deletePlanetById(PLANET_RESPONSE.id());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        StepVerifier.create(result.getBody())
                .verifyComplete();
    }
}