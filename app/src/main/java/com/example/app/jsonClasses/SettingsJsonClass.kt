package com.example.app.jsonClasses

/**
 * Класс хранения настроек приложения для их хранения в Json формате
 */
data class SettingsJsonClass (
    val wasRegistered: Boolean,
    val colorTheme: Boolean,
    val appPassword: String
)