package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class ChangingPinAcceptActivity : ComponentActivity() {

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    private var index: Int = 0
    private var password = arrayOf(0, 0, 0, 0, 0)

    /**
     * Массив виджетов для отображения количества введённых символов пароля
     */
    private val widgetArray = arrayOf(R.id.circleOneAccept, R.id.circleTwoAccept, R.id.circleThreeAccept, R.id.circleFourAccept, R.id.circleFiveAccept)

    private lateinit var passwordEntered: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changing_pin_accept)
        passwordEntered = intent.getStringExtra("Password").toString()
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme(getColorTheme())
    }

    /**
     * Получаем флаг входа в приложение
     */
    private fun getWasRegisteredFlag() : Boolean {
        return settings.getBoolean("WasRegistered", false)
    }

    /**
     * Функция получения цветовой темы приложения из SharedPreferences
     * @return lightThemeFlag
     */
    private fun getColorTheme() : Boolean {
        val lightThemeFlag: Boolean = settings.getBoolean("ColorTheme", true)
        Toast.makeText(applicationContext, "Light theme, $lightThemeFlag", Toast.LENGTH_LONG).show()
        return lightThemeFlag
    }

    /**
     * Функция установки цветовой темы, исходя из установленных настроек
     * @param colorTheme
     */
    private fun setColorTheme(colorTheme: Boolean) {
        val mainLayout = findViewById<RelativeLayout>(R.id.main_layout)
        if(colorTheme) {
            mainLayout.setBackgroundColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.GRAY)
        }
    }

    /**
     * Переходим в меню настроек
     */
    private fun changeActivitySettings() {
        val intent = Intent(this@ChangingPinAcceptActivity, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Переходим на главную страницу приложения
     */
    private fun changeActivityMain() {
        val intent = Intent(this@ChangingPinAcceptActivity, MainAppPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Сохраняем пароль в файл с настройками
     */
    private fun savePasswordToSettings() {
        val editor = settings.edit()
        Toast.makeText(applicationContext, "New password: ${passwordToString()}", Toast.LENGTH_LONG).show()
        editor.putString("Password", passwordToString())
        editor.commit()
    }

    /**
     * Преобразуем пароль в строку
     */
    private fun passwordToString(): String{
        var string = ""
        string += password[0].toString()
        string += password[1].toString()
        string += password[2].toString()
        string += password[3].toString()
        string += password[4].toString()
        return string
    }

    /**
     * Функция смены пароля и выхода со страницы
     */
    private fun saveNewPassword(){
        savePasswordToSettings()
        if(getWasRegisteredFlag()) {
            changeActivitySettings()
        } else {
            changeWasRegisteredFlag()
            changeActivityMain()
        }
    }

    /**
     * Меняем флаг входа в приложение
     */
    private fun changeWasRegisteredFlag() {
        val editor = settings.edit()
        editor.putBoolean("WasRegistered", true)
        editor.commit()
    }

    /**
     * Методя для изменения отображения количества введённых символов пароля
     * @param index
     * @param paintTrue
     */
    private fun updateCircles(index: Int, paintTrue: Boolean) {
        val circleToUpdate = findViewById<TextView>(widgetArray[index])
        circleToUpdate.backgroundTintMode = null
        if(paintTrue) {
            circleToUpdate.setBackgroundResource(R.drawable.circle_on)
        } else {
            circleToUpdate.setBackgroundResource(R.drawable.circle_off)
        }
    }

    /**
     * Обработка некорректного пароля
     */
    private fun incorrectInput() {
        for(i in widgetArray.indices){
            updateCircles(i, false)
            password = arrayOf(0, 0, 0, 0, 0)
        }
        index = 0
    }

    /**
     * Обработка ввода 5 символов пароля
     */
    private fun checkPassword() {
        if(passwordEntered == passwordToString()){
            saveNewPassword()
        } else {
            incorrectInput()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 0
     */
    fun onZeroClicked(view: View) {
        password[index] = 0
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 1
     */
    fun onOneClicked(view: View) {
        password[index] = 1
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 2
     */
    fun onTwoClicked(view: View) {
        password[index] = 2
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 3
     */
    fun onThreeClicked(view: View) {
        password[index] = 3
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 4
     */
    fun onFourClicked(view: View) {
        password[index] = 4
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 5
     */
    fun onFiveClicked(view: View) {
        password[index] = 5
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 6
     */
    fun onSixClicked(view: View) {
        password[index] = 6
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 7
     */
    fun onSevenClicked(view: View) {
        password[index] = 7
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 8
     */
    fun onEightClicked(view: View) {
        password[index] = 8
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 9
     */
    fun onNineClicked(view: View) {
        password[index] = 9
        updateCircles(index, true)
        ++index
        if(index == 5){
            checkPassword()
        }
    }


    /**
     * Метод для обработки нажатия кнопки backspace
     */
    fun onBackSpaceClicked(view: View) {
        password[index] = -1
        if(index > 0) {
            --index
        }
        updateCircles(index, false)
    }
}