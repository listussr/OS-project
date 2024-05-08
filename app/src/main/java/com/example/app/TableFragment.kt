package com.example.app

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginStart
import com.example.app.databinding.FragmentPieChartBinding
import com.example.app.databinding.FragmentTableBinding
import com.example.app.dataprocessing.TableInfoClass


class TableFragment : Fragment() {
    private var _binding : FragmentTableBinding? = null
    private val binding get() = _binding!!

    var listOfInfo: List<TableInfoClass> = listOf(
        TableInfoClass(false, "Еда", "03 мар 2024", 1000),
        TableInfoClass(false, "Транспорт", "08 мар 2024", 5000)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        updateTable(TableInfoClass(
                true,
                "Food",
                "03 march 2024",
                1000
            ))
        return binding.root
    }

    private fun updateTable(info: TableInfoClass){
        val tableRow = TableRow(context)

        // LinearLayout
        val linearLayout = LinearLayout(context)
        val params = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        params.setMargins(40, 0, 0, 0) // last argument here is the bottom margin
        linearLayout.layoutParams = params
        linearLayout.setBackgroundResource(R.drawable.table_border)

        // ImageView
        val operationFlag = ImageView(context)
        operationFlag.setImageResource(R.drawable.backspace)
        val paramsFlagView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsFlagView.setMargins(5, 0, 0, 0) // last argument here is the bottom margin
        operationFlag.layoutParams = paramsFlagView
        Log.v("App", "Created layout row")

        // TextView
        val categoryView = TextView(context)
        val paramsCategoryView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsCategoryView.setMargins(25, 0, 0, 0) // last argument here is the bottom margin
        categoryView.layoutParams = paramsCategoryView
        categoryView.text = info.categoryName
        setViewParams(categoryView)

        val dateView = TextView(context)
        val paramsDateView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsDateView.setMargins(70, 0, 0, 0) // last argument here is the bottom margin
        dateView.layoutParams = paramsDateView
        dateView.text = info.date
        setViewParams(dateView)

        val sumView = TextView(context)
        val paramsSumView = TableRow.LayoutParams( // if the parent is FrameLayout, this should be FrameLayout.LayoutParams
            LinearLayout.LayoutParams.WRAP_CONTENT, // modify this if its not wrap_content
            LinearLayout.LayoutParams.WRAP_CONTENT // modify this if its not wrap_content
        )
        paramsSumView.setMargins(70, 0, 0, 0) // last argument here is the bottom margin
        sumView.layoutParams = paramsSumView
        sumView.text = info.summaryMoney.toString()
        setViewParams(sumView)

        linearLayout.addView(operationFlag)
        linearLayout.addView(categoryView)
        linearLayout.addView(dateView)
        linearLayout.addView(sumView)

        tableRow.addView(linearLayout)

        binding.table.addView(tableRow)
    }

    private fun setViewParams(view: TextView){
        val typeFace: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.montserrat)
        view.typeface = typeFace
        view.textSize = 17f
        view.typeface = Typeface.DEFAULT_BOLD
    }

    companion object {
        @JvmStatic
        fun newInstance() = TableFragment()
    }
}