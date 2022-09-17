package com.example.fitnessapp.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adapter.ExersizeModel

class NewVIewModel : ViewModel() { // изменения
    val mutableListExersize = MutableLiveData<ArrayList<ExersizeModel>>() // передаем адаптеру не знаем открылся ли фрагмент и поэтому передаем мутабле лайф дата, который следит
var pref: SharedPreferences? = null
    var currentNumber = 0
    fun savePref(key: String, value : Int){
        pref?.edit()?.putInt(key, value)?.apply()
    }
    fun getExersizeCount() : Int{
        return pref?.getInt(currentNumber.toString(), 0) ?: 0
    }
}