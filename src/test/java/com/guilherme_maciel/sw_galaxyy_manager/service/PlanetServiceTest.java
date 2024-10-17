package com.guilherme_maciel.sw_galaxyy_manager.service;

import com.guilherme_maciel.sw_galaxyy_manager.common.PlanetCommons;
import com.guilherme_maciel.sw_galaxyy_manager.entity.Planet;
import com.guilherme_maciel.sw_galaxyy_manager.exception.PlanetNotFoundException;
import com.guilherme_maciel.sw_galaxyy_manager.repository.PlanetRepository;
import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PlanetServiceTest {

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks PlanetService planetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlanet_WithValidPlanetRequest_ReturnCreatedPlanet() {
        when(planetRepository.save(any(Planet.class))).thenReturn(Mono.just(PlanetCommons.PLANET));
        Mono<PlanetResponse> sut = planetService.createPlanet(PlanetCommons.PLANET_REQUEST);

        StepVerifier.create(sut)
                .expectNext(PlanetCommons.PLANET_RESPONSE)
                .verifyComplete();

        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    void listAllPlanets_WithValidName_ReturnPlanets() {
        when(planetRepository.findAll(any(Example.class), any(Sort.class))).thenReturn(Flux.just(PlanetCommons.PLANET));
        Flux<PlanetResponse> sut = planetService.listAllPlanets("Tatooine");

        StepVerifier.create(sut)
                .expectNext(PlanetCommons.PLANET_RESPONSE)
                .verifyComplete();

        verify(planetRepository, times(1)).findAll(any(Example.class), any(Sort.class));
    }

    @Test
    void listAllPlanets_WithInvalidName_ReturnPlanets() {
        when(planetRepository.findAll(any(Example.class), any(Sort.class))).thenReturn(Flux.empty());
        Flux<PlanetResponse> sut = planetService.listAllPlanets("Tatooine");

        StepVerifier.create(sut)
                .verifyComplete();

        verify(planetRepository, times(1)).findAll(any(Example.class), any(Sort.class));
    }

    @Test
    void listPlanetById_WithValidId_ReturnPlanet() {
        when(planetRepository.findById(anyLong())).thenReturn(Mono.just(PlanetCommons.PLANET));
        Mono<PlanetResponse> sut = planetService.listPlanetById(1L);

        StepVerifier.create(sut)
                .expectNext(PlanetCommons.PLANET_RESPONSE)
                .verifyComplete();

        verify(planetRepository, times(1)).findById(anyLong());
    }

    @Test
    void listPlanetById_WithInvalidId_ReturnPlanetNotFoundException() {
        when(planetRepository.findById(anyLong())).thenReturn(Mono.empty());
        Mono<PlanetResponse> sut = planetService.listPlanetById(1L);

        StepVerifier.create(sut)
                .expectError(PlanetNotFoundException.class)
                .verify();

        verify(planetRepository, times(1)).findById(anyLong());
    }

    @Test
    void updatePlanetById_WithValidId_ReturnUpdatedPlanet() {
        when(planetRepository.findById(anyLong())).thenReturn(Mono.just(PlanetCommons.PLANET));
        when(planetRepository.save(any(Planet.class))).thenReturn(Mono.just(PlanetCommons.UPDATED_PLANET));
        Mono<PlanetResponse> sut = planetService.updatePlanetById(1L, PlanetCommons.UPDATED_PLANET_REQUEST);

        StepVerifier.create(sut)
                .expectNext(PlanetCommons.UPDATED_PLANET_RESPONSE)
                .verifyComplete();

        verify(planetRepository, times(1)).findById(anyLong());
        verify(planetRepository, times(1)).save(any(Planet.class));
    }

    @Test
    void updatePlanetById_WithInvalidId_ReturnPlanetNotFoundException() {
        when(planetRepository.findById(anyLong())).thenReturn(Mono.empty());
        Mono<PlanetResponse> sut = planetService.updatePlanetById(1L, PlanetCommons.UPDATED_PLANET_REQUEST);

        StepVerifier.create(sut)
                .expectError(PlanetNotFoundException.class)
                .verify();

        verify(planetRepository, times(1)).findById(anyLong());
    }

    @Test
    void deletePlanetById_WithValidId_ReturnVoid() {
        when(planetRepository.findById(anyLong())).thenReturn(Mono.just(PlanetCommons.PLANET));
        when(planetRepository.deleteById(anyLong())).thenReturn(Mono.empty());

        Mono<Void> sut = planetService.deletePlanetById(1L);

        StepVerifier.create(sut)
                .verifyComplete();

        verify(planetRepository, times(1)).findById(anyLong());
        verify(planetRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletePlanetById_WithInvalid_ReturnPlanetNotFoundException() {
        when(planetRepository.findById(anyLong())).thenReturn(Mono.empty());
        Mono<Void> sut = planetService.deletePlanetById(1L);

        StepVerifier.create(sut)
                .expectError(PlanetNotFoundException.class)
                .verify();

        verify(planetRepository, times(1)).findById(anyLong());
    }
}
