package com.slaxation.kotlinMoro.mapper

import com.slaxation.kotlinMoro.dto.UserDTO
import com.slaxation.kotlinMoro.model.User
import org.mapstruct.Mapper

@Mapper
interface UserMapper : EntityDTOMapper<User,UserDTO> {
}