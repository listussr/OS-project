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
        setThemeButtonsState()
        return binding.root
    }

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

    private fun onLightThemeButtonClicked() {
        binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_off)
        binding.lightThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
        binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_on)
        binding.darkThemeButton.setTextColor(Color.BLACK)
        changeTheme(true)
        binding.fragmentLayoutSettings.setBackgroundColor(Color.parseColor("#F1F1F1"))
    }

    private fun onDarkThemeButtonClicked() {
        binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_off)
        binding.darkThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
        binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_on)
        binding.lightThemeButton.setTextColor(Color.BLACK)
        changeTheme(false)
        binding.fragmentLayoutSettings.setBackgroundColor(Color.parseColor("#1E1E1E"))
    }

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

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}