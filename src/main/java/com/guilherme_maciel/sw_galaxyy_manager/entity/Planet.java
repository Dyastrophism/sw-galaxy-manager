package com.guilherme_maciel.sw_galaxyy_manager.entity;

import com.guilherme_maciel.sw_galaxyy_manager.usecases.planetusecase.PlanetRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "planets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Planet {

    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("climate")
    private String climate;
    @Column("terrain")
    private String terrain;
    @Column("population")
    private Long population;

    public Planet fromRequest(PlanetRequest request) {
        return new Planet(null, request.name(), request.climate(), request.terrain(), request.population());
    }
}
