package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.databinding.DayListItemBinding
import com.example.fitnessapp.databinding.ExersizeListFragmentBinding
import com.example.fitnessapp.databinding.ExersizeListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExersizeAdapter (): ListAdapter <ExersizeModel, ExersizeAdapter.ExersizeHolder> (MyComparator()){
    class ExersizeHolder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ExersizeListItemBinding.bind (view)
        fun setData (exersize: ExersizeModel) = with(binding) {
            tvName.text= exersize.name
            tvCount.text= exersize.time
            cb.isChecked = exersize.isDone
imExs.setImageDrawable(GifDrawable(root.context.assets, exersize.image))// котекс из рут а рут в байдинге а байдинг это активити у которой есть доступ ко всем ресурсам
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExersizeHolder { // здесь создается шаблон
       val view = LayoutInflater.from(parent.context).inflate(R.layout.exersize_list_item, parent, false)
        return ExersizeHolder(view)
    }

    override fun onBindViewHolder(holder: ExersizeHolder, position: Int) { // здесь заполняется
       holder.setData(getItem(position))
    }
    class MyComparator : DiffUtil.ItemCallback <ExersizeModel> () { //для сравнения
        override fun areItemsTheSame(oldItem: ExersizeModel, newItem: ExersizeModel): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: ExersizeModel, newItem: ExersizeModel): Boolean {
            return oldItem==newItem
        }

    }

}