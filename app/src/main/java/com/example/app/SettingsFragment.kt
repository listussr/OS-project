package com.example.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.SharedPreferences
import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.app.databinding.FragmentSettingsBinding
import com.example.app.databinding.FragmentTableBinding
import com.google.android.material.color.MaterialColors.getColorStateList

class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = context?.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.darkThemeButton.setOnClickListener{ onDarkThemeButtonClicked() }
        binding.lightThemeButton.setOnClickListener { onLightThemeButtonClicked() }
        binding.changePasswordButton.setOnClickListener { onChangePINButtonClicked() }
        binding.englishLanguageButton.setOnClickListener { onEnglishClicked() }
        binding.russianLanguageButton.setOnClickListener { onRussianClicked() }
        setThemeButtonsState()
        setLanguage()
        return binding.root
    }

    /**
     * Переводим надписи на View при входе на fragment
     */
    private fun setLanguage() {
        if(getLanguageFlag()){
            translateToRussian()
        } else {
            translateToEnglish()
        }
    }

    /**
     * Переводим все View на английский язык
     */
    private fun translateToEnglish() {
        binding.textViewThemeSettings.text = getString(R.string.t_theme_eng)
        binding.darkThemeButton.text = getString(R.string.t_dark_theme_eng)
        binding.lightThemeButton.text = getString(R.string.t_light_theme_eng)
        binding.changePasswordButton.text = getString(R.string.t_change_password_eng)
        binding.leaveProfileButton.text = getString(R.string.t_exit_button_eng)
        requireActivity().findViewById<TextView>(R.id.reportTextViewMain)
            .text = getString(R.string.t_settings_page_eng)
        requireActivity().findViewById<TextView>(R.id.textViewOperationNav)
            .text = getString(R.string.t_operations_eng)
        requireActivity().findViewById<TextView>(R.id.textViewReportNav)
            .text = getString(R.string.t_report_eng)
        requireActivity().findViewById<TextView>(R.id.textViewPlanNav)
            .text = getString(R.string.t_plan_eng)
        requireActivity().findViewById<TextView>(R.id.textViewSettingsNav)
            .text = getString(R.string.t_settings_page_eng)
    }

    /**
     * Переводим все View на русский язык
     */
    private fun translateToRussian() {
        binding.textViewThemeSettings.text = getString(R.string.t_theme)
        binding.darkThemeButton.text = getString(R.string.t_dark_theme)
        binding.lightThemeButton.text = getString(R.string.t_light_theme)
        binding.changePasswordButton.text = getString(R.string.t_change_password)
        binding.leaveProfileButton.text = getString(R.string.t_exit_button)
        requireActivity().findViewById<TextView>(R.id.reportTextViewMain)
            .text = getString(R.string.t_settings_page)
        requireActivity().findViewById<TextView>(R.id.textViewOperationNav)
            .text = getString(R.string.t_operations)
        requireActivity().findViewById<TextView>(R.id.textViewReportNav)
            .text = getString(R.string.t_report)
        requireActivity().findViewById<TextView>(R.id.textViewPlanNav)
            .text = getString(R.string.t_plan)
        requireActivity().findViewById<TextView>(R.id.textViewSettingsNav)
            .text = getString(R.string.t_settings_page)
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", true)
    }

    /**
     * Устанавливаем кнопки тем в правильное положение согласно настройкам
     */
    private fun setThemeButtonsState() {
        val lightThemeFlag: Boolean = settings.getBoolean("ColorTheme", true)
        Toast.makeText(context, "Light theme, $lightThemeFlag", Toast.LENGTH_LONG).show()
        if(lightThemeFlag){
            binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_off)
            binding.lightThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
            binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_on)
            binding.darkThemeButton.setTextColor(Color.BLACK)
        } else {
            binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_off)
            binding.darkThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
            binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_on)
            binding.lightThemeButton.setTextColor(Color.BLACK)
        }
    }

    /**
     * Обработка нажатия кнопки светлой темы
     */
    private fun onLightThemeButtonClicked() {
        binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_off)
        binding.lightThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
        binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_on)
        binding.darkThemeButton.setTextColor(Color.BLACK)
        changeTheme(true)
        binding.fragmentLayoutSettings.setBackgroundColor(Color.parseColor("#F1F1F1"))
    }

    /**
     * Обработка нажатия кнопки тёмной темы
     */
    private fun onDarkThemeButtonClicked() {
        binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_off)
        binding.darkThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
        binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_on)
        binding.lightThemeButton.setTextColor(Color.BLACK)
        changeTheme(false)
        binding.fragmentLayoutSettings.setBackgroundColor(Color.parseColor("#1E1E1E"))
    }

    /**
     * Обработка нажатия на кнопку смены пароля
     */
    private fun onChangePINButtonClicked() {
        val intent = Intent(this.context, ChangingPinActivity::class.java)
        startActivity(intent)
        this.requireActivity().finish()
    }

    /**
     * Меняем флаг светлой темы в настройках приложения
     */
    private fun changeTheme(lightThemeFlag: Boolean) {
        val editor = settings.edit()
        editor.putBoolean("ColorTheme", lightThemeFlag)
        editor.commit()
    }

    /**
     * Выставляем английский язык в настройках приложения
     */
    private fun onEnglishClicked() {
        val editor = settings.edit()
        editor.putBoolean("Language", false)
            .commit()
        translateToEnglish()
    }

    /**
     * ставим русский язык в настройках приложения
     */
    private fun onRussianClicked() {
        val editor = settings.edit()
        editor.putBoolean("Language", true)
            .commit()
        translateToRussian()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}