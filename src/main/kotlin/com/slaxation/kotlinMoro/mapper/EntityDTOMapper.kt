package com.slaxation.kotlinMoro.mapper

import org.mapstruct.MappingTarget
import java.io.Serializable

interface EntityDTOMapper<ENTITY : Serializable, DTO> {

    fun entityToDto(entity: ENTITY): DTO

    fun dtoToEntity(dto: DTO): ENTITY

    fun entityToDto(entity: ENTITY, @MappingTarget target: DTO): DTO

    fun dtoToEntity(dto: DTO, @MappingTarget entity: ENTITY): ENTITY
}