package com.guilherme_maciel.sw_galaxyy_manager.repository;

import com.guilherme_maciel.sw_galaxyy_manager.entity.Planet;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends ReactiveCrudRepository<Planet, Long>, ReactiveQueryByExampleExecutor<Planet> {
}
