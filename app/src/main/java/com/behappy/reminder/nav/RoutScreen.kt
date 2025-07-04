package com.behappy.reminder.nav

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
sealed interface RoutScreen

@Serializable
data object RoutMainScreen : RoutScreen

@Serializable
data class RoutAddEditScreen(val id: Long? = null) : RoutScreen

fun NavBackStackEntry?.routeClass(): KClass<*>? {
    return this?.destination?.route
        ?.split("?")
        ?.first()
        ?.let { Class.forName(it) }
        ?.kotlin
}