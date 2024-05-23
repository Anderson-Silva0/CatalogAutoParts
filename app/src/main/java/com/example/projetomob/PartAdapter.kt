package com.example.projetomob

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PartAdapter(private val pecas: List<Part>) : RecyclerView.Adapter<PartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewList: ImageView
        val descricaoTextView: TextView
        init {
            imageViewList = view.findViewById(R.id.imageViewList)
            descricaoTextView = view.findViewById(R.id.descricao_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.part_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagemByteArray = pecas[position].image

        val bitmapImage = imagemByteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }

        holder.imageViewList.setImageBitmap(bitmapImage)

        val descricao = pecas[position].description
        holder.descricaoTextView.text = descricao
    }

    override fun getItemCount(): Int {
        return pecas.size
    }

}