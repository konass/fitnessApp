package com.example.fitnessapp.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.adapter.ExersizeModel
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.ExersizeBinding
import com.example.fitnessapp.utils.FragmentManager
import com.example.fitnessapp.utils.NewVIewModel
import com.example.fitnessapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable


class ExersizeFragment : Fragment() {
private var timer : CountDownTimer? = null
private lateinit var binding: ExersizeBinding
private var exersizeCounter= 0
    private var currentDay = 0
    private var exList: ArrayList<ExersizeModel>? = null
    private var ab: ActionBar? = null
    private val model: NewVIewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = ExersizeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentNumber
        exersizeCounter = model.getExersizeCount()
        ab=(activity as AppCompatActivity).supportActionBar
        model.mutableListExersize.observe(viewLifecycleOwner){
            exList=it
            nextExersize()
        }
binding.bNext.setOnClickListener{
    nextExersize()
}
    }



private fun nextExersize(){
    if (exersizeCounter < exList?.size!!) { // как позиция
        val ex = exList?.get(exersizeCounter++) ?: return //проверка на нал
showExersize(ex)
        setExersizeType(ex)
        showNextExersize()
    }
    else {
        exersizeCounter++
FragmentManager.setFragment(DayFinishFragment.newInstance(), activity as AppCompatActivity)
    }
}
    private fun showExersize (exersize: ExersizeModel) = with (binding) {
        imMain.setImageDrawable( GifDrawable(root.context.assets, exersize.image))
        tvName.text=exersize.name
        val title = "$exersizeCounter / ${exList?.size}"
        ab?.title= title
    }
    private fun setExersizeType (exersize : ExersizeModel){
        if (exersize.time.startsWith("x")){
binding.tvTime.text=exersize.time
        }
        else {
startTimer(exersize)
        }
    }

    private fun showNextExersize()= with(binding){
        if (exersizeCounter<exList?.size!!) { // как позиция
            val ex = exList?.get(exersizeCounter) ?: return //проверка на нал
            imNext.setImageDrawable( GifDrawable(root.context.assets, ex.image))

            setTimeType(ex)
        }
        else {
            imNext.setImageDrawable( GifDrawable(root.context.assets, "congrats-congratulations.gif"))
       tvNext.text= getString(R.string.done)
        }
    }
    private fun setTimeType (ex: ExersizeModel){
        if (ex.time.startsWith("x")){
            binding.tvNext.text=ex.time
        }
        else {
            val name = ex.name + ": ${TimeUtils.getTime(ex.time.toLong() * 1000) }"
           binding.tvNext.text = name

        }
    }
    private fun startTimer(exersize : ExersizeModel) = with(binding){ // счетчик
        progressBar.max=exersize.time.toInt() * 1000
        timer?.cancel()
        timer = object: CountDownTimer(exersize.time.toLong() * 1000, 1){ // ОБЪЕКТ ДЛЯ ВЫЗОВА КОЛБЭКА
            override fun onTick(restTime: Long) {
                tvTime.text= TimeUtils.getTime(restTime)
                progressBar.progress=restTime.toInt()
            }

            override fun onFinish() {
                nextExersize()
                  }

        }.start()
    }




    override fun onDetach() {
        super.onDetach()
        model.savePref(currentDay.toString(),exersizeCounter-1)
        timer?.cancel()
    }
    companion object {
         @JvmStatic
         // интсанция если фрагмент запущен то мы просто включаем его инстанцию а не создаем новый
        fun newInstance() = ExersizeFragment()

    }
}