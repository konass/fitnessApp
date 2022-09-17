package com.example.fitnessapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnessapp.R

object FragmentManager {
    var currentFragment : Fragment? = null
    fun setFragment (newFragment: Fragment, act : AppCompatActivity){ // в активити находится метод супорт фрагмент мэнэджер
val transaction =act.supportFragmentManager.beginTransaction() // для показывания фрагментов
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.placeHolder, newFragment) // 1 место куда помещаем фрагмент 2 новый фрагмент
        transaction.commit() // применить все изменения
        currentFragment = newFragment
    }
}