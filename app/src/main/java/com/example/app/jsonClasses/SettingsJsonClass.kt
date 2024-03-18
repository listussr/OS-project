package com.example.app.jsonClasses

/**
 * Класс хранения настроек приложения для их хранения в Json формате
 */
data class SettingsJsonClass (
    var wasRegistered: Boolean,
    var colorTheme: Boolean,
    var appPassword: String
){
    override fun toString(): String {
        return "wasRegistered: ${wasRegistered}, colorTheme: ${colorTheme}, appPassword: $appPassword"
    }
}