package com.example.app

import android.os.Bundle
import android.view.View
import android.content.Context
import androidx.activity.ComponentActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityPinCodeEnteringBinding
import java.io.IOException
import java.nio.charset.Charset

class PINCodeActivity : AppCompatActivity() {

    /**
     * Пароль приложения из настроек
     */
    private var appPassword: Array<Int> = arrayOf(1, 2, 3, 4, 5)

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    /**
     * Массив для набираемого пароля
     */
    private var password: Array<Int> = arrayOf(0, 0, 0, 0, 0)

    /**
     * Индекс в массиве для вводимого символа
     */
    private var index: Int = 0

    /**
     * Массив виджетов для отображения количества введённых символов пароля
     */
    private val widgetArray = arrayOf(R.id.circleOneEnter, R.id.circleTwoEnter, R.id.circleThreeEnter, R.id.circleFourEnter, R.id.circleFiveEnter)

    private lateinit var binding: ActivityPinCodeEnteringBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinCodeEnteringBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme(getColorTheme())
        setLanguageInViews()
        appPassword = unmaskPassword(getAppPassword())
    }

    /**
     * Устанавливаем язык во всех виджетах на activity
     */
    private fun setLanguageInViews() {
        val language = getLanguageFlag()
        if(!language){
            binding.textViewApproveEnterPin.text = getString(R.string.t_enter_password_eng)
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", false)
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
     * Получаем пароль из SharedPreferences
     * @return String? strPassword
     */
    private fun getAppPassword(): String? {
        val strPassword: String?
        if(settings.contains("Password")){
            strPassword = settings.getString("Password", null)
        }
        else{
            strPassword = "00000"
            val editor = settings.edit()
            editor.putString("Password", strPassword)
            editor.commit()
        }
        Toast.makeText(applicationContext, "Password, $strPassword", Toast.LENGTH_LONG).show()
        return strPassword
    }

    private fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = assets.open("com/example/app/jsonClasses/resources/Settings.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    /**
     * Получаем численное значение пароля из строки
     * @param strPassword
     * @return Array<Int>
     */
    private fun unmaskPassword(strPassword: String?): Array<Int> {
        return arrayOf(
            strPassword!![0].toString().toInt(),
            strPassword[1].toString().toInt(),
            strPassword[2].toString().toInt(),
            strPassword[3].toString().toInt(),
            strPassword[4].toString().toInt()
            )
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
     * Метод для переключения между страницами
     */
    private fun changeActivity() {
        val intent = Intent(this@PINCodeActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Метод для проверки корректности пароля
     */
    private fun comparePasswords(view: View){
        if(password contentEquals appPassword){
            changeActivity()
        }
        else {
            incorrectInput()
            Toast.makeText(applicationContext, R.string.msg_incorrect_password, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 0
     */
    fun onZeroClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 0
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 1
     */
    fun onOneClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 1
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 2
     */
    fun onTwoClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 2
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 3
     */
    fun onThreeClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 3
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 4
     */
    fun onFourClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 4
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 5
     */
    fun onFiveClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 5
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 6
     */
    fun onSixClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 6
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 7
     */
    fun onSevenClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 7
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 8
     */
    fun onEightClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 8
        if(index == 5){
            comparePasswords(view)
        }
    }

    /**
     * Метод для обработки нажатия кнопки 9
     */
    fun onNineClicked(view: View) {
        updateCircles(index, true)
        password[index++] = 9
        if(index == 5){
            comparePasswords(view)
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
