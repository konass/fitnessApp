package com.example.fitnessapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.DaysAdapter
import com.example.adapter.DayModel
import com.example.adapter.ExersizeAdapter
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.ExersizeListFragmentBinding
import com.example.fitnessapp.databinding.FragmentDaysBinding
import com.example.fitnessapp.utils.FragmentManager
import com.example.fitnessapp.utils.NewVIewModel


class ExersizeListFragment : Fragment() {
private lateinit var binding: ExersizeListFragmentBinding
private lateinit var adapter : ExersizeAdapter
    private var ab: ActionBar? = null
    private val model: NewVIewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    binding =ExersizeListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutableListExersize.observe(viewLifecycleOwner){
            for ( i in 0 until model.getExersizeCount()){
                it[i] = it [i].copy( isDone = true)
            }
            adapter.submitList(it)
        }

    }

private fun init () = with(binding){
    adapter= ExersizeAdapter()
    rcView.layoutManager= LinearLayoutManager (activity) // указали что эдементы идут по вертикали
    rcView.adapter=adapter
    //ab=(activity as AppCompatActivity).supportActionBar
   // ab?.title= getString(R.string.exersizes)
    bStart.setOnClickListener{
        FragmentManager.setFragment(WaitingFragment.newInstance(), activity as AppCompatActivity)
    }

}


    companion object {
         @JvmStatic
         // интсанция если фрагмент запущен то мы просто включаем его инстанцию а не создаем новый
        fun newInstance() = ExersizeListFragment()

    }
}