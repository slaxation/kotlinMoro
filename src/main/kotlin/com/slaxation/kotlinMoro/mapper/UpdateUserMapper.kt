package com.slaxation.kotlinMoro.mapper

import com.slaxation.kotlinMoro.dto.UpdateUserDTO
import com.slaxation.kotlinMoro.model.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UpdateUserMapper : EntityDTOMapper<User, UpdateUserDTO> {
}