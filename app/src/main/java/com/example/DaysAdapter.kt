package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.DayModel
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.DayListItemBinding

class DaysAdapter (var listener: Listener): ListAdapter <DayModel, DaysAdapter.DayHolder> (MyComparator()){
    class DayHolder (view: View): RecyclerView.ViewHolder(view){
        private val binding = DayListItemBinding.bind (view)
        fun setData (day: DayModel, listener: Listener) = with(binding) {
            val name = root.context.getString(R.string.day) + " ${adapterPosition + 1}"// for find resourses root- context
textViewName.text =name
            val exCounter = day.exercises.split(",").size.toString() + " " + root.context.getString(R.string.exersize)
            TextViewCounter.text = exCounter
            checkBox.isChecked = day.isDone
            itemView.setOnClickListener{ listener.onClick(day.copy(dayNumber = adapterPosition +1))}
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder { // здесь создается шаблон
       val view = LayoutInflater.from(parent.context).inflate(R.layout.day_list_item, parent, false)
        return DayHolder(view)
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) { // здесь заполняется
       holder.setData(getItem(position), listener)
    }
    class MyComparator : DiffUtil.ItemCallback <DayModel> () { //для сравнения
        override fun areItemsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: DayModel, newItem: DayModel): Boolean {
            return oldItem==newItem
        }

    }
    interface Listener {
        fun onClick (day: DayModel)
    }
}