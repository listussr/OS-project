package com.example.app.piechart

import android.os.Parcelable
import android.view.View.BaseSavedState
import com.example.app.dataprocessing.MoneyInteractionClass

/**
 * Собственный state для сохранения и восстановление данных
 */
class PieChartState(
    private val superSavedState: Parcelable?,
    val dataList: Array<MoneyInteractionClass>
) : BaseSavedState(superSavedState), Parcelable {
}