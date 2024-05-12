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
    private var rectangleType: Boolean = true
    private var paintR: Paint = Paint()
    private var paintC: Paint = Paint()
    private var paintT: Paint = Paint()

    private var startAngle = 0f
    private var sweepAngle = 0f
    private var textStartPointX = 0f
    private var textStartPointY = 0f

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
        if(listOfInfo.isNotEmpty()) {
            for(i in listOfInfo){
                Log.v("App", i.toString())
            }
            drawCircle(canvas)
            Log.v("App", "Draw circle")
            drawLegend(canvas)
        } else {
            Log.v("App", "List is empty")
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
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return PieChartState(superState, listOfInfo)
    }

    /**
     * Устанавливаем значение внутри круговой диаграммы
     */
    fun setInfoList(list: Array<MoneyInteractionClass>) {
        listOfInfo = list
    }

    /**
     * Считаем сколько процентов от диаграммы занимает конкретный раздел
     */
    private fun countPercentage() : ArrayList<Float> {
        var sum = 0f
        for(i in listOfInfo.indices){
            sum += listOfInfo[i].value
        }
        Log.d("App", "Sum: $sum")
        val listOfPercents = ArrayList<Float>()
        for(i in listOfInfo.indices){
            Log.d("App", "Element: ${listOfInfo[i]}")
            listOfPercents.add((listOfInfo[i].value / sum) * 100f)
            Log.v("App", "Percents: ${(listOfInfo[i].value / sum).toFloat()}")
        }
        return listOfPercents
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

    /**
     * Функция отрисовки текста "легенды"
     */
    private fun drawLegend(canvas: Canvas) {
        for(i in listOfInfo.indices) {
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

    override fun invalidate() {
        startAngle = 0f
        sweepAngle = 0f
        textStartPointX = 0f
        textStartPointY = 0f
        paintC.style = Paint.Style.STROKE

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