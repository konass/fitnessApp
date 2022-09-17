package com.example.fitnessapp.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.DaysAdapter
import com.example.adapter.DayModel
import com.example.adapter.ExersizeModel
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.FragmentDaysBinding
import com.example.fitnessapp.utils.DialogManager
import com.example.fitnessapp.utils.FragmentManager
import com.example.fitnessapp.utils.NewVIewModel


class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter : DaysAdapter
private lateinit var binding: FragmentDaysBinding
    private var ab: ActionBar? = null
private val model: NewVIewModel by activityViewModels() // для безопасной перердачи между фрагментами
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentNumber = 0
        initRCView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      return inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset){
            DialogManager.showDialog(activity as AppCompatActivity, R.string.reset_days_message,
            object : DialogManager.Listener{
                override fun onClick() {
                    model.pref?.edit()?.clear()?.apply()
                    adapter.submitList(fillDaysArray())
                }
            })

        }
        return super.onOptionsItemSelected(item)
    }
    private fun initRCView() = with(binding) {
         adapter = DaysAdapter(this@DaysFragment)
        ab=(activity as AppCompatActivity).supportActionBar
        ab?.title= getString(R.string.days)
        rcViewDays.layoutManager= LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter =adapter
        adapter.submitList(fillDaysArray())
    }


private fun fillDaysArray() : ArrayList<DayModel> {
    val tArray = ArrayList<DayModel> ()
    var daysDoneCounter = 0
    resources.getStringArray(R.array.day_exercises).forEach {
        model.currentNumber++
        val exCounter = it.split(",").size
        tArray.add(DayModel(it, 0,model.getExersizeCount()==exCounter))
    }
    binding.pb.max = tArray.size
tArray.forEach {
    if (it.isDone) daysDoneCounter++
}
    updateRestDaysUI (tArray.size - daysDoneCounter, tArray.size)
return tArray
}
private fun updateRestDaysUI (restDays : Int, days: Int) = with(binding){
    val rDays = getString(R.string.res) +" $restDays " + getString(R.string.rest_days)
tvRaceDays.text= rDays
    pb.progress=days-restDays
}
    private fun fillExersizeList(day: DayModel){
        val tempList= ArrayList <ExersizeModel> ()
        day.exercises.split(",").forEach{ // массив с номерами упражнений
            val exersizeList= resources.getStringArray(R.array.exercise) // массив с данными для упражнения
           val exersize = exersizeList[it.toInt()]
            val exersizeArray=exersize.split("|")
            tempList.add(ExersizeModel(exersizeArray[0],exersizeArray[1],false, exersizeArray[2] ))
        }
        model.mutableListExersize.value = tempList
    }
    companion object {
         @JvmStatic
         // интсанция если фрагмент запущен то мы просто включаем его инстанцию а не создаем новый
        fun newInstance() = DaysFragment()

    }

    override fun onClick(day: DayModel) {
        if (!day.isDone){
        fillExersizeList(day)
        model.currentNumber= day.dayNumber
FragmentManager.setFragment(ExersizeListFragment.newInstance(), activity as AppCompatActivity)
            }
        else {
            DialogManager.showDialog(activity as AppCompatActivity, R.string.reset_day_message,
                object : DialogManager.Listener{
                    override fun onClick() {
                        model.savePref(day.dayNumber.toString(),0)
                        fillExersizeList(day)
                        model.currentNumber= day.dayNumber
                        FragmentManager.setFragment(ExersizeListFragment.newInstance(), activity as AppCompatActivity)
                    }
                })

        }
        }

}