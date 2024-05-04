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

    private var paintColumnIncome: Paint = Paint()
    private var paintColumnExpenses: Paint = Paint()
    private var paintRect: Paint = Paint()
    private var paintText: Paint = Paint()

    private var listOfInfo: List<Pair<Int, Int>> = listOf(
        Pair(10000, 9000),
        Pair(11000, 8000),
        Pair(12000, 10000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
        Pair(10000, 9000),
    )
    init {
        paintColumnIncome.style = Paint.Style.FILL
        paintColumnExpenses.style = Paint.Style.FILL
        paintRect.style = Paint.Style.STROKE
        paintText.style = Paint.Style.FILL

        paintColumnIncome.color = Color.parseColor("#1F5CB6")
        paintColumnExpenses.color = Color.parseColor("#F25757")
        paintRect.color = Color.BLACK
        paintText.color = Color.BLACK
        paintText.textSize = 28f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val flag: Boolean = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //drawRectangle(canvas)
        drawCoordinates(canvas)
        drawColumns(canvas)
        drawText(canvas)
        drawLegend(canvas)
    }

    private fun drawRectangle(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintRect)
    }

    private fun drawCoordinates(canvas: Canvas) {
        canvas.drawLine(110f, height.toFloat() - 65f, width.toFloat() - 40f, height.toFloat() - 65f, paintRect)
        canvas.drawLine(110f, 20f, 110f, height.toFloat() - 65f, paintRect)
    }

    private fun findMaxValue() : Int {
        var maxValue = 0
        listOfInfo.forEach {month ->
            maxValue = maxOf(maxValue, maxOf(month.second, month.first))
        }
        return maxValue
    }

    private fun drawColumns(canvas: Canvas) {
        val maxValue = findMaxValue()

        val dX = (width.toFloat() - 250f) / 14f
        var startPointX = 100 + dX
        var widthOfColumn = 15f

        for (i in listOfInfo.indices) {
            val heightOfColumnIncome = listOfInfo[i].first * height / maxValue
            canvas.drawRect(
                startPointX,
                height.toFloat() - 65f,
                startPointX + widthOfColumn,
                height - heightOfColumnIncome.toFloat(),
                paintColumnIncome
            )
            startPointX += widthOfColumn
            val heightOfColumnExpenses = listOfInfo[i].second * height / maxValue
            canvas.drawRect(
                startPointX,
                height.toFloat() - 65f,
                startPointX + widthOfColumn,
                height - heightOfColumnExpenses.toFloat(),
                paintColumnExpenses
            )
            startPointX += dX
        }
    }

    private fun drawText(canvas: Canvas) {
        val dX = (width.toFloat() - 250f) / 14f
        var textStartPointX = 100 + dX
        val textPointY = height.toFloat() - 35f

        val maxValue = findMaxValue()
        val dVal = maxValue / 10f
        var value = dVal
        val dY = (height.toFloat() - 45f) / 10f
        var moneyPointY = height + 35f - dY
        for(i in 0..11){
            canvas.drawText(
                monthsDigit[i],
                textStartPointX,
                textPointY,
                paintText
            )
            textStartPointX += dX + 15f
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

    private fun drawLegend(canvas: Canvas) {
        var startX = (width) / 3f
        val startY = height - 20f
        paintRect.style = Paint.Style.FILL
        paintRect.color = Color.parseColor("#1F5CB6")
        canvas.drawRect(startX, startY, startX + 20f, startY + 20f, paintRect)
        startX += 40f
        canvas.drawText(
            "Доходы",
            startX,
            startY + 20f,
            paintText
        )
        startX += 250f
        paintRect.color = Color.parseColor("#F25757")
        canvas.drawRect(startX, startY, startX + 20f, startY + 20f, paintRect)
        startX += 40f
        canvas.drawText(
            "Расходы",
            startX,
            startY + 20f,
            paintText
        )
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

    override fun invalidate() {
        paintRect.color = Color.BLACK
        super.invalidate()
    }

    fun setInfoList(list: List<Pair<Int, Int>>) {
        listOfInfo = list
    }
}