package com.gigacrew.dreamjournal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigacrew.dreamjournal.databinding.ItemDreamEntryBinding

class DreamListAdapter(
    private val dreamList: List<Dream>,
    private val listener: OnItemClickListener,
    private val deleteListener:OnDeleteClickListener
):RecyclerView.Adapter<DreamListAdapter.DreamViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(dream: Dream)
    }
    interface OnDeleteClickListener {
        fun onDeleteClick(dream:Dream)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DreamViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDreamEntryBinding.inflate(layoutInflater,parent,false)
        return DreamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val dream = dreamList[position]
        holder.binding.TitleTextView.text = dream.title
        holder.binding.dateTextView.text = dream.date
        holder.binding.descriptionTextView.text = dream.dream_description
        holder.itemView.setOnClickListener{
            listener.onItemClick(dream)
        }
        /* if we decide to make the dream deletable from listview
        holder.binding.deleteButton.setOnClickListener{
        deleteListener.onDeleteClick(dream)
        }
         */
    }

    override fun getItemCount(): Int {
        return dreamList.size
    }

    class DreamViewHolder(val binding: ItemDreamEntryBinding):RecyclerView.ViewHolder(binding.root)

}
