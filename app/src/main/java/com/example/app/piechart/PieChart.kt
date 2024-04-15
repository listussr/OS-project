package com.example.app.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Color
import android.os.Parcelable


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

    companion object {
        private val DEFAULT_MARGIN_TEXT_RIGHT: Int = 2
        private val DEFAULT_MARGIN_TEXT_LEFT: Int = 10
        private val DEFAULT_MARGIN_TEXT_BOTTOM: Int = 2
        private val DEFAULT_MARGIN_TEXT_TOP: Int = 2
    }

    private var marginTextLeft: Float = 0.0f
    private var marginTextRight: Float = 0.0f
    private var marginTextBottom: Float = 5.0f
    private var marginTextTop: Float = 55.0f
    private var rectangleColor: String = "#000000"
    private lateinit var listOfInfo: List<Pair<Int, String>>
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
        val width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.UNSPECIFIED)
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
        paintT.textSize = 25f
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        if(listOfInfo != null) {
            drawRectangle(canvas)
            drawCircle(canvas)
            drawLegend(canvas)
        } else {
            val xCoordinate = (width / 3).toFloat()
            val yCoordinate = (height / 2).toFloat()
            val radius = (width / 4).toFloat()
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintR)
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

        listOfInfo = pieChartState?.dataList ?: listOf()
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return PieChartState(superState, listOfInfo)
    }

    fun setInfoList(list: List<Pair<Int, String>>) {
        listOfInfo = list
    }

    /**
     * Функция отрисовки диаграммы
     */
    private fun drawCircle(canvas: Canvas) {
        val xCoordinate = (width / 3).toFloat()
        val yCoordinate = (height / 2).toFloat()
        val radius = (width / 4).toFloat()
        for(i in listOfInfo.indices){
            paintC.color = colorArray[i]
            startAngle += sweepAngle
            sweepAngle = 360f * listOfInfo[i].first / 100f
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
                listOfInfo[i].second,
                textStartPointX,
                textStartPointY,
                paintT
            )
            paintC.color = colorArray[i]
            paintC.style = Paint.Style.FILL
            canvas.drawCircle(
                textStartPointX - 30f,
                textStartPointY,
                15f,
                paintC
            )
        }
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