package com.example.picsum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(
    private val items: MutableList<ImageItem> = mutableListOf()
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    fun setItems(newItems: List<ImageItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val img = itemView.findViewById<ImageView>(R.id.itemImage)
        private val authorTv = itemView.findViewById<TextView>(R.id.itemAuthor)
        private val idTv = itemView.findViewById<TextView>(R.id.itemId)

        fun bind(item: ImageItem) {
            authorTv.text = item.author
            idTv.text = "ID: ${item.id}"
            Glide.with(itemView.context)
                .load(item.downloadUrl)
                .centerCrop()
                .into(img)
        }
    }
}