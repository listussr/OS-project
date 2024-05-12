package com.example.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel(){
    val message: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }
}