package com.example.fininfocomtask.Realm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fininfocomtask.databinding.UserRowBinding
import com.kanyideveloper.realmdatabasedemo.UserData

class UserAdapter(
    private val onClickListener: OnClickListener,
    val sortBy: SortBy,
    private val onSwipe: OnSwiper
) :
    ListAdapter<UserData, UserAdapter.MyViewHolder>(MyDiffUtil) {

    object MyDiffUtil : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class MyViewHolder(private val binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData?) {
            binding.titleTextView.text = userData?.name
            binding.tvAge.text = userData?.age
            binding.tvCity.text = userData?.city
            binding.descriptionTextView.text = userData?.description
            binding.imageSort.setOnClickListener {
                sortBy.sortBy()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UserRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            val note = getItem(position)
            holder.bind(note)
            onSwipe.onClick(note)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(note)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class OnClickListener(val clickListener: (note: UserData) -> Unit) {
        fun onClick(note: UserData) = clickListener(note)
    }

    class OnSwiper(val clickListener: (note: UserData) -> Unit) {
        fun onClick(note: UserData) = clickListener(note)
    }

    interface SortBy {
        fun sortBy()
    }

}