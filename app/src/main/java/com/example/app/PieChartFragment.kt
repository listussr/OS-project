package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.databinding.FragmentPieChartBinding

class PieChartFragment : Fragment(R.layout.fragment_pie_chart) {

    private val listOfInfoIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(10, "Contribution"), Pair(10, "Friends"))
    private val listOfInfoExpenses: List<Pair<Int, String>> = listOf(Pair(10, "Food"), Pair(10, "Car"), Pair(30, "GKH"), Pair(50, "For future"))

    private var periodNum = 1

    private var _binding : FragmentPieChartBinding? = null
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
        _binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCurrentMonthClicked()
        onLastMonthClicked()
        onThreeMonthsClicked()
        onCurrentYearClicked()
        translateViews()
    }

    override fun onStart() {
        super.onStart()
        binding.pieChartExpenses.setInfoList(listOfInfoExpenses)
        binding.pieChartIncome.setInfoList(listOfInfoIncome)
    }

    private fun translateViews() {
        if(!getLanguageFlag()) {
            binding.IncomeTextViewPlan.text = getString(R.string.t_income_eng)
            binding.ExpensesTextViewPlan.text = getString(R.string.t_expenses_eng)
            binding.buttonCurrentMonth.text = "April"
            binding.buttonLastMonth.text = "March"
            binding.buttonThreeMonths.text = getString(R.string.t_3_months_eng)
            binding.buttonCurrentYear.text = getString(R.string.t_2024)
        }
    }

    /**
     * Получаем из настроек язык приложения
     */
    private fun getLanguageFlag() : Boolean {
        return settings.getBoolean("Language", true)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onCurrentMonthClicked() {
        binding.buttonCurrentMonth.setOnClickListener{
            val currentIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(20, "Contribution"))
            val currentExpenses: List<Pair<Int, String>> = listOf(Pair(5, "Games"), Pair(20, "Car"), Pair(20, "Food"), Pair(30, "Gkh"), Pair(25, "Investments"))
            binding.pieChartExpenses.setInfoList(currentExpenses)
            binding.pieChartIncome.setInfoList(currentIncome)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsCurrentMonth()
        }
    }

    private fun onLastMonthClicked() {
        binding.buttonLastMonth.setOnClickListener{
            val currentIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(20, "Contribution"))
            val currentExpenses: List<Pair<Int, String>> = listOf(Pair(5, "Games"), Pair(20, "Car"), Pair(20, "Food"), Pair(30, "Gkh"), Pair(25, "Investments"))
            binding.pieChartExpenses.setInfoList(currentExpenses)
            binding.pieChartIncome.setInfoList(currentIncome)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsLastMonth()
        }
    }

    private fun onThreeMonthsClicked() {
        binding.buttonThreeMonths.setOnClickListener{
            val currentIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(20, "Contribution"))
            val currentExpenses: List<Pair<Int, String>> = listOf(Pair(5, "Games"), Pair(20, "Car"), Pair(20, "Food"), Pair(30, "Gkh"), Pair(25, "Investments"))
            binding.pieChartExpenses.setInfoList(currentExpenses)
            binding.pieChartIncome.setInfoList(currentIncome)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsThreeMonths()
        }
    }

    private fun onCurrentYearClicked() {
        binding.buttonCurrentYear.setOnClickListener{
            val currentIncome: List<Pair<Int, String>> = listOf(Pair(80, "Job"), Pair(20, "Contribution"))
            val currentExpenses: List<Pair<Int, String>> = listOf(Pair(5, "Games"), Pair(20, "Car"), Pair(20, "Food"), Pair(30, "Gkh"), Pair(25, "Investments"))
            binding.pieChartExpenses.setInfoList(currentExpenses)
            binding.pieChartIncome.setInfoList(currentIncome)
            binding.pieChartExpenses.invalidate()
            binding.pieChartIncome.invalidate()
            changeButtonsCurrentYear()
        }
    }

    private fun changeButtonsCurrentMonth() {
        binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_on)
        binding.buttonCurrentMonth.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            2 -> {
                binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                binding.buttonLastMonth.setTextColor(Color.BLACK)
            }
            3 -> {
                binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                binding.buttonThreeMonths.setTextColor(Color.BLACK)
            }
            4 -> {
                binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                binding.buttonCurrentYear.setTextColor(Color.BLACK)
            }
        }
        periodNum = 1
    }

    private fun changeButtonsLastMonth() {
        binding.buttonLastMonth.setBackgroundResource(R.drawable.rect_on)
        binding.buttonLastMonth.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            1 -> {
                binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            }
            3 -> {
                binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                binding.buttonThreeMonths.setTextColor(Color.BLACK)
            }
            4 -> {
                binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                binding.buttonCurrentYear.setTextColor(Color.BLACK)
            }
        }
        periodNum = 2
    }

    private fun changeButtonsThreeMonths() {
        binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect_on)
        binding.buttonThreeMonths.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            1 -> {
                binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            }
            2 -> {
                binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                binding.buttonLastMonth.setTextColor(Color.BLACK)
            }
            4 -> {
                binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_off)
                binding.buttonCurrentYear.setTextColor(Color.BLACK)
            }
        }
        periodNum = 3
    }

    private fun changeButtonsCurrentYear() {
        binding.buttonCurrentYear.setBackgroundResource(R.drawable.half_roundrect_right_on)
        binding.buttonCurrentYear.setTextColor(Color.parseColor("#F1F1F1"))
        when(periodNum){
            1 -> {
                binding.buttonCurrentMonth.setBackgroundResource(R.drawable.half_roundrect_left_off)
                binding.buttonCurrentMonth.setTextColor(Color.BLACK)
            }
            2 -> {
                binding.buttonLastMonth.setBackgroundResource(R.drawable.rect)
                binding.buttonLastMonth.setTextColor(Color.BLACK)
            }
            3 -> {
                binding.buttonThreeMonths.setBackgroundResource(R.drawable.rect)
                binding.buttonThreeMonths.setTextColor(Color.BLACK)
            }
        }
        periodNum = 4
    }

    companion object {
        @JvmStatic
        fun newInstance() = PieChartFragment()
    }
}