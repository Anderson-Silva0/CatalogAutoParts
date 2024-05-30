package com.example.projetomob

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PartAdapter(private val pecas: ArrayList<Part>) : RecyclerView.Adapter<PartAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewList: ImageView
        val descricaoTextView: TextView
        val btEdicao: Button
        val btDelete: Button
        val dbController = DataBaseController(view.context)

        init {
            imageViewList = view.findViewById(R.id.imageViewList)
            descricaoTextView = view.findViewById(R.id.descricao_tv)
            btEdicao = view.findViewById<Button>(R.id.btEdit)
            btDelete = view.findViewById<Button>(R.id.btDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.part_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = pecas[position].id
        val imagemByteArray = pecas[position].image

        val bitmapImage = imagemByteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }
        holder.imageViewList.setImageBitmap(bitmapImage)

        val descricao = pecas[position].description
        holder.descricaoTextView.text = descricao

        holder.btEdicao.setOnClickListener {
            val intent = Intent(holder.itemView.context, ActivityEdit::class.java)

            intent.putExtra("TAG_ID", id)
            intent.putExtra("TAG_IMAGE", imagemByteArray)
            intent.putExtra("TAG_DESCRIPTION", descricao)

            holder.itemView.context.startActivity(intent)
        }

        holder.btDelete.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setTitle("Confirmação")
            alertDialogBuilder.setMessage("Tem certeza que deseja excluir este item?")
            alertDialogBuilder.setPositiveButton("Sim") { dialog, _ ->

                holder.dbController.deleteById(id)
                val newParts = ArrayList(pecas)

                newParts.removeAt(position)
                pecas.clear()
                pecas.addAll(newParts)
                notifyItemRemoved(position)
                val itemChangedCount = pecas.size - position
                notifyItemRangeChanged(position, itemChangedCount)

                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

    }

    override fun getItemCount(): Int {
        return pecas.size
    }

}