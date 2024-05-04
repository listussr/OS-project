package com.example.app.barchart

import android.os.Parcelable
import android.view.View.BaseSavedState

/**
 * Собственный state для сохранения и восстановление данных
 */
class BarChartState(
    private val superSavedState: Parcelable?,
    val dataList: List<Pair<Int, Int>>
) : BaseSavedState(superSavedState), Parcelable {
}