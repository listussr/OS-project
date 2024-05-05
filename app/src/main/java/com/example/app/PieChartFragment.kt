package com.example.app

import android.content.Intent
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

    private var _binding : FragmentPieChartBinding? = null
    private val binding get() = _binding!!
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
    }

    override fun onStart() {
        super.onStart()
        binding.pieChartExpenses.setInfoList(listOfInfoExpenses)
        binding.pieChartIncome.setInfoList(listOfInfoIncome)
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
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PieChartFragment()
    }
}