package com.example.app.diagrams

import android.os.Parcelable
import android.view.View.BaseSavedState

/**
 * Собственный state для сохранения и восстановление данных
 */
class PieChartState(
    private val superSavedState: Parcelable?,
    val dataList: List<Pair<Int, String>>
) : BaseSavedState(superSavedState), Parcelable {
}