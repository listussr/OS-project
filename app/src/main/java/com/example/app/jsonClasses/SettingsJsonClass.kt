package com.example.app.jsonClasses

/**
 * Класс хранения настроек приложения для их хранения в Json формате
 */
data class SettingsJsonClass (
    /**
     * Флаг для начала работы с приложением
     */
    var wasRegistered: Boolean,
    /**
     * Флаг для установки тёмной темы оформления
     */
    var colorTheme: Boolean,
    /**
     * Строковая версия пароля
     */
    var appPassword: String
){
    override fun toString(): String {
        return "wasRegistered: ${wasRegistered}, colorTheme: ${colorTheme}, appPassword: $appPassword"
    }
}