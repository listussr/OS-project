package com.example.app.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.util.Log
import com.example.app.R
import com.example.app.dataprocessing.CategoryClass
import com.example.app.dataprocessing.MoneyInteractionClass
import kotlin.random.Random


/**
 * class for drawing PieChart diagramm
 * @property listOfInfo информация для диаграммы
 * @property marginTextLeft  отступ текста слева
 * @property marginTextTop отступ текста сверху
 * @property marginTextBottom отступ текста свизу
 * @property marginTextRight отступ текста справа
 * @property rectangleColor цвет прямоугольника
 * @property rectangleType вид прямоугольника: закрашенный полностью / закрашена только граница
 * @property
 */
class PieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    private var marginTextLeft: Float = 0.0f
    private var marginTextRight: Float = 0.0f
    private var marginTextBottom: Float = 5.0f
    private var marginTextTop: Float = 55.0f
    private var rectangleColor: String = "#000000"
    private lateinit var listOfInfo: Array<MoneyInteractionClass>
    private lateinit var listOfCategory: Array<CategoryClass>
    private var rectangleType: Boolean = true
    private var paintR: Paint = Paint()
    private var paintC: Paint = Paint()
    private var paintT: Paint = Paint()

    private var startAngle = 0f
    private var sweepAngle = 0f
    private var textStartPointX = 0f
    private var textStartPointY = 0f
    private var categorySum: ArrayList<Float> = ArrayList()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    init {
        paintR.style = Paint.Style.STROKE
        paintR.color = Color.GRAY
        paintR.strokeWidth = 7f

        paintC.style = Paint.Style.STROKE
        paintC.color = Color.CYAN
        paintC.strokeWidth = 25f

        paintT.color = Color.BLACK
        paintT.style = Paint.Style.FILL
        paintT.textSize = 30f
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            paintT.typeface = resources.getFont(R.font.montserrat)
        }
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        if(listOfInfo.isNotEmpty() && listOfCategory.isNotEmpty()) {
            for(i in listOfInfo){
                Log.v("App", i.toString())
            }
            drawCircle(canvas)
            Log.v("App", "Draw circle")
            drawLegend(canvas)
        } else {
            Log.e("App", "ListOfInfo empty: ${listOfInfo.isEmpty()}, ListOfCategory empty: ${listOfCategory.isEmpty()}")
            val xCoordinate = (width / 3).toFloat()
            val yCoordinate = (height / 2).toFloat()
            val radius = (2 * width / 9).toFloat()
            canvas.drawCircle(xCoordinate, yCoordinate, radius, paintC)
            textStartPointY = marginTextTop
            textStartPointX = (width / 4) * 3f
            canvas.drawText("No info", textStartPointX, textStartPointY, paintT)
        }
    }

    /**
     * Функция отрисовки заднего фона прямоугольника, в границах которго содержится диаграмма
     */
    private fun drawRectangle(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintR)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val pieChartState = state as? PieChartState
        super.onRestoreInstanceState(state)

        listOfInfo = pieChartState?.dataList ?: arrayOf()
        listOfCategory = pieChartState?.categoryList ?: arrayOf()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return PieChartState(superState, listOfInfo, listOfCategory)
    }

    /**
     * Устанавливаем значение внутри круговой диаграммы
     */
    fun setInfoList(list: Array<MoneyInteractionClass>, categoryList: Array<CategoryClass>) {
        listOfInfo = list
        listOfCategory = categoryList
    }

    /**
     * Считаем сколько процентов от диаграммы занимает конкретный раздел
     */
    private fun countPercentage() : ArrayList<Float> {
        var sum = 0f
        countSumInEveryCategory()
        for(i in categorySum.indices) {
            Log.w("App", "i=${i}, categorySum.size=${categorySum.size}, listOfCategory.size=${listOfCategory.size}")
//            Log.v("App", "Category ${listOfCategory[i].name}: sum inside = ${categorySum[i]}")
            sum += categorySum[i]
        }
        Log.d("App", "Sum: $sum")
        val listOfPercents = ArrayList<Float>()
        for(i in categorySum.indices) {
            listOfPercents.add((categorySum[i] / sum) * 100f)
            Log.e("App", "Percents for ${listOfCategory[i].name}: ${(categorySum[i] / sum).toFloat()}")
        }
        return listOfPercents
    }

    /**
     * Считаем суммарные расходы в каждой категории
     */
    private fun countSumInEveryCategory() {
        Log.v("App", "List of category size = ${listOfCategory.size}")
        for(category in listOfCategory){
            var sum = 0f
            for(moneyInteraction in listOfInfo){
                if(moneyInteraction.category.name == category.name){
                    Log.w("App", "Category: ${category.name}, money: categ. ${moneyInteraction.category.name}, val. ${moneyInteraction.value}")
                    sum += moneyInteraction.value
                    Log.w("App", "Sum = $sum")
                }
            }
            categorySum.add(sum)
        }
    }

    /**
     * Функция отрисовки диаграммы
     */
    private fun drawCircle(canvas: Canvas) {
        val xCoordinate = (width / 3).toFloat()
        val yCoordinate = (height / 2).toFloat()
        val radius = (2 * width / 9).toFloat()
        val percentageList = countPercentage()
        Log.v("App", "Counted percents")
        for(i in percentageList.indices){
            if(categorySum[i] != 0f) {
                paintC.color = colorArray[i % 4]
                startAngle += sweepAngle
                sweepAngle = 360f * percentageList[i] / 100f
                canvas.drawArc(
                    xCoordinate - radius,
                    yCoordinate - radius,
                    xCoordinate + radius,
                    yCoordinate + radius,
                    startAngle,
                    sweepAngle,
                    false,
                    paintC
                )
            }
        }
    }

    /**
     * Функция отрисовки текста "легенды"
     */
    private fun drawLegend(canvas: Canvas) {
        Log.v("App", "Drawing legend")
        for(i in listOfCategory.indices) {
            if(categorySum[i] != 0f) {
                textStartPointY += marginTextTop + if (i > 0) marginTextBottom else 0f
                textStartPointX = (width / 4) * 3f
                canvas.drawText(
                    listOfInfo[i].category.name,
                    textStartPointX,
                    textStartPointY,
                    paintT
                )
                paintC.color = colorArray[i % 4]
                paintC.style = Paint.Style.FILL
                canvas.drawCircle(
                    textStartPointX - 30f,
                    textStartPointY,
                    15f,
                    paintC
                )
            }
        }
    }

    override fun invalidate() {
        startAngle = 0f
        sweepAngle = 0f
        textStartPointX = 0f
        textStartPointY = 0f
        paintC.style = Paint.Style.STROKE
        categorySum.clear()
        super.invalidate()
    }

    /**
     * Функция для разделения входной строки на несколько, чтобы ничего не терялось при выходе за страницу
     */
    /*
    private fun createMultilineText(
      text: String,
      possibleWidth: Float
    ): Array<String> {

        if(text.length > possibleWidth) {
            val words = text.trim().split("\\s+".toRegex()).toTypedArray()
            var line: String
            var charCounter: Int
            for(i in words){

            }
        } else {

        }
        return arrayOf()
    }
     */
}