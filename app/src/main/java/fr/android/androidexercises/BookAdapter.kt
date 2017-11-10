package fr.android.androidexercises

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

internal class BookAdapter(private val inflater: LayoutInflater, private val books: List<Book>) : RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(this.inflater.inflate(R.layout.custom_view_item_book, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder.itemView) {
            is BookItemView -> holder.itemView.bindView(books[position])
            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return this.books.size
    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}