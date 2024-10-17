package com.guilherme_maciel.sw_galaxyy_manager.util;


import com.guilherme_maciel.sw_galaxyy_manager.entity.Planet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {

    private QueryBuilder() {
    }

    public static Example<Planet> makeQuery(Planet planet) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase()
                .withIgnoreNullValues();
        return Example.of(planet, exampleMatcher);
    }
}
