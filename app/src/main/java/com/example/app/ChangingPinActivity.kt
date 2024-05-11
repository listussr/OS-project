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
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityChangingPinBinding
import com.example.app.databinding.ActivityLoginBinding

class ChangingPinActivity : AppCompatActivity() {

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    private var index: Int = 0
    private var password = arrayOf(0, 0, 0, 0, 0)

    private lateinit var binding: ActivityChangingPinBinding

    /**
     * Массив виджетов для отображения количества введённых символов пароля
     */
    private val widgetArray = arrayOf(R.id.circleOneChange, R.id.circleTwoChange, R.id.circleThreeChange, R.id.circleFourChange, R.id.circleFiveChange)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangingPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme(getColorTheme())
        setLanguageInViews()
    }

    /**
     * Устанавливаем язык во всех виджетах на activity
     */
    private fun setLanguageInViews() {
        val language = getLanguageFlag()
        if(!language){
            binding.textViewApproveChangePinAccept.text = getString(R.string.t_imagine_password_eng)
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", false)
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
     * Меняем флаг входа в приложение
     */


    /**
     * Переходим в меню настроек
     */
    private fun changeActivitySettings() {
        val intent = Intent(this@ChangingPinActivity, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Переходим на главную страницу приложения
     */
    private fun changeActivityMain() {
        val intent = Intent(this@ChangingPinActivity, MainAppPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Переходим на страницу подтверждения пароля
     */
    private fun changeActivityApprovePin() {
        val intent = Intent(this@ChangingPinActivity, ChangingPinAcceptActivity::class.java)
        intent.putExtra("Password", passwordToString())
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
        /*savePasswordToSettings()
        if(getWasRegisteredFlag()) {
            changeActivitySettings()
        } else {
            changeWasRegisteredFlag()
            changeActivityMain()
        }*/
        changeActivityApprovePin()
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
        //circleToUpdate.backgroundTintMode = null
        if(paintTrue) {
            circleToUpdate.setBackgroundResource(R.drawable.circle_on)
        } else {
            circleToUpdate.setBackgroundResource(R.drawable.circle_off)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 0
     */
    fun onZeroClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 0
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 1
     */
    fun onOneClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 1
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 2
     */
    fun onTwoClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 2
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 3
     */
    fun onThreeClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 3
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 4
     */
    fun onFourClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 4
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 5
     */
    fun onFiveClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 5
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 6
     */
    fun onSixClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 6
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 7
     */
    fun onSevenClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 7
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 8
     */
    fun onEightClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 8
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 9
     */
    fun onNineClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 9
        if(index == 5){
            saveNewPassword()
        }
    }


    /**
     * Метод для обработки нажатия кнопки backspace
     */
    fun onBackSpaceClicked(view: View) {
        if(index > 0) {
            --index
        }
        updateCircles(index, false)
    }
}