package com.example.app.barchart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Color
import android.os.Parcelable

class BarChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    companion object{
        val neverUsed = 0
    }

    private var paintColumnIncome: Paint = Paint()
    private var paintColumnExpenses: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var paintText: Paint = Paint()

    private var listOfInfo: List<Pair<String, Pair<Int, Int>>> = listOf(
        Pair("Январь", Pair(10000, 9000)),
        Pair("Февраль", Pair(11000, 8000)),
        Pair("Март", Pair(12000, 10000)),
        Pair("Апрель", Pair(10000, 9000)),
        Pair("Май", Pair(10000, 9000)),
        Pair("Июнь", Pair(10000, 9000)),
        Pair("Июль", Pair(10000, 9000)),
        Pair("Август", Pair(10000, 9000)),
        Pair("Сентябрь", Pair(10000, 9000)),
        Pair("Октябрь", Pair(10000, 9000)),
        Pair("Ноябрь", Pair(10000, 9000)),
        Pair("Декабрь", Pair(10000, 9000)),
    )
    init {
        paintColumnIncome.style = Paint.Style.FILL
        paintColumnExpenses.style = Paint.Style.FILL
        paintRect.style = Paint.Style.STROKE
        paintText.style = Paint.Style.FILL

        paintColumnIncome.color = Color.BLUE
        paintColumnExpenses.color = Color.MAGENTA
        paintRect.color = Color.BLACK
        paintText.color = Color.BLACK
        paintText.textSize = 25f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val flag: Boolean = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRectangle(canvas)
        drawCoordinates(canvas)
        drawColumns(canvas)
        drawText(canvas)
    }

    private fun drawRectangle(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintRect)
    }

    private fun drawCoordinates(canvas: Canvas) {
        canvas.drawLine(40f, height.toFloat() - 20f, width.toFloat() - 40f, height.toFloat() - 20f, paintRect)
        canvas.drawLine(40f, 20f, 40f, height.toFloat() - 20f, paintRect)
    }

    private fun findMaxValue() : Int {
        var maxValue = 0
        listOfInfo.forEach {month ->
            maxValue = maxOf(maxValue, maxOf(month.second.second, month.second.first))
        }
        return maxValue
    }

    private fun drawColumns(canvas: Canvas) {
        val maxValue = findMaxValue()

        val dX = (width.toFloat() - 150f) / 14f
        var startPointX = dX
        var widthOfColumn = 15f

        for (i in listOfInfo.indices) {
            val heightOfColumnIncome = listOfInfo[i].second.first * height / maxValue
            canvas.drawRect(
                startPointX,
                height.toFloat() - 20f,
                startPointX + widthOfColumn,
                height - heightOfColumnIncome.toFloat(),
                paintColumnIncome
            )
            startPointX += widthOfColumn
            val heightOfColumnExpenses = listOfInfo[i].second.second * height / maxValue
            canvas.drawRect(
                startPointX,
                height.toFloat() - 20f,
                startPointX + widthOfColumn,
                height - heightOfColumnExpenses.toFloat(),
                paintColumnExpenses
            )
            startPointX += dX
        }
    }

    private fun drawText(canvas: Canvas) {
        val dX = (width.toFloat()) / 14f
        var textStartPointX = dX
        val textPointY = height.toFloat() - 5f

        val maxValue = findMaxValue()
        val dVal = maxValue / 10f
        var value = dVal
        val dY = (height.toFloat() - 10f) / 10f
        var moneyPointY = height + 35f - dY
        for(i in 0..11){
            canvas.drawText(
                monthsDigit[i],
                textStartPointX,
                textPointY,
                paintText
            )
            textStartPointX += dX
        }
        for (i in 0..9){
            canvas.drawText(
                value.toString(),
                3f,
                moneyPointY,
                paintText
            )
            moneyPointY -= dY
            value += dVal
        }


    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val barChartState = state as? BarChartState
        super.onRestoreInstanceState(state)

        listOfInfo = barChartState?.dataList ?: listOf()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return BarChartState(superState, listOfInfo)
    }

    fun setInfoList(list: List<Pair<String, Pair<Int, Int>>>) {
        listOfInfo = list
    }
}