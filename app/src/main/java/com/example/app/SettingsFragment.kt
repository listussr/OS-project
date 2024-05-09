package com.example.app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.SharedPreferences
import android.graphics.Color
import android.view.ViewGroup
import com.example.app.databinding.FragmentSettingsBinding
import com.example.app.databinding.FragmentTableBinding
import com.google.android.material.color.MaterialColors.getColorStateList

class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    //private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //settings = context?.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.darkThemeButton.setOnClickListener{ onDarkThemeButtonClicked() }
        binding.lightThemeButton.setOnClickListener { onLightThemeButtonClicked() }

        return binding.root
    }

    private fun onLightThemeButtonClicked() {
        binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_off)
        binding.lightThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
        binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_on)
        binding.darkThemeButton.setTextColor(Color.BLACK)
    }

    private fun onDarkThemeButtonClicked() {
        binding.lightThemeButton.setBackgroundResource(R.drawable.half_roundrect_left_off)
        binding.darkThemeButton.setTextColor(Color.parseColor("#F1F1F1"))
        binding.darkThemeButton.setBackgroundResource(R.drawable.half_roundrect_right_on)
        binding.lightThemeButton.setTextColor(Color.BLACK)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}