package com.example.musicloversappneofonie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicloversappneofonie.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_chip.view.*

class ChipAdapter : RecyclerView.Adapter<ChipAdapter.ChipViewHolder>(){

    private val chips by lazy { mutableListOf<String>() }

    fun setChips(chips: List<String>) {
        if (chips.isNotEmpty()){
            this.chips.clear()
        }
        this.chips.addAll(chips)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_chip, parent, false)

        return ChipViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chips.size
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        val chip = chips[position]
        holder.bind(chip)
    }

    class ChipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chip: String){
            with(itemView){
                chip_text.text = chip
            }
        }
    }

}