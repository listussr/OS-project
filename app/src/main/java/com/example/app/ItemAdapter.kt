package com.example.app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ItemsRowBinding
import com.example.app.dataprocessing.Item

class ItemAdapter(
    private val items: ArrayList<Item>
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.text.text = item.text
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item.text)
            }
        }
        if(!item.flag) {
            holder.text.setTextColor(Color.parseColor("#F1F3F6"))
            holder.mLayout.setBackgroundResource(R.drawable.roundrect_dark)
            holder.linLayout.setBackgroundResource(R.drawable.roundrect_dark_gray)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: String)
    }

    class ViewHolder(binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val text = binding.text
        val linLayout = binding.linLayoutHolder
        val mLayout = binding.llMain
    }
}
