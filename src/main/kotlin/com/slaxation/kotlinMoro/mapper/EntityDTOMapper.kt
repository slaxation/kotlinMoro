package com.slaxation.kotlinMoro.mapper

import java.io.Serializable

interface EntityDTOMapper<ENTITY : Serializable, DTO> {

    fun entityToDto(entity: ENTITY): DTO

    fun dtoToEntity(dto: DTO): ENTITY

    fun entityToDto(entity: ENTITY, target: DTO): DTO

    fun dtoToEntity(dto: DTO, entity: ENTITY): ENTITY
}