package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context, private val listener : clickListener) : RecyclerView.Adapter<NewsViewholder>() {

    val items: ArrayList<NewsDataClass> = ArrayList()
    val con : Context =  context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemnews,
                                                                parent, false)


        val itemholder = NewsViewholder(view)

        view.setOnClickListener(){
            listener.onItemsClicked(items[itemholder.absoluteAdapterPosition])
        }

        return itemholder
    }

    override fun onBindViewHolder(holder: NewsViewholder, position: Int) {
        val current = items[position]
        Glide.with(con).load(current.urlImage).into(holder.imageOfNews)
        holder.titleOfNews.text = current.title
        holder.authorOfNews.text = current.author
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun updateNews(newsUpdate : ArrayList<NewsDataClass>){
        items.clear()
        items.addAll(newsUpdate)

        notifyDataSetChanged()
    }
}

class NewsViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageOfNews : ImageView = itemView.findViewById(R.id.newsImage)
    val titleOfNews:TextView = itemView.findViewById(R.id.newsTitle)
    val authorOfNews:TextView = itemView.findViewById(R.id.newsAuthor)
}

interface clickListener{
    fun onItemsClicked(items: NewsDataClass)
}