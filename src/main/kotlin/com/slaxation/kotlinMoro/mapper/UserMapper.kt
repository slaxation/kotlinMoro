package com.slaxation.kotlinMoro.mapper

import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.model.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper : EntityDTOMapper<User,UserDTO> {
}