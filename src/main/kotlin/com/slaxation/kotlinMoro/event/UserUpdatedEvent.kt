package com.slaxation.kotlinMoro.event

import org.springframework.context.ApplicationEvent

class UserUpdatedEvent(
    source: Any?,
    private val username: String
) : ApplicationEvent(source!!)