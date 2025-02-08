package com.slaxation.kotlinMoro.event.listener

import com.slaxation.kotlinMoro.dto.UserDTO
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserLogoutListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun logoutUserAfterCommit(userDTO: UserDTO) {
        SecurityContextHolder.clearContext()
    }
}
