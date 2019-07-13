package ru.zennex.zennexapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.quote_list_item.view.*
import ru.zennex.zennexapp.extensions.inflate
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.model.Quote

class QuotesAdapter(var quotes: List<Quote>): RecyclerView.Adapter<QuoteHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): QuoteHolder {
        return QuoteHolder(p0.inflate(R.layout.quote_list_item))
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: QuoteHolder, position: Int) {
        holder.bindQuote(quotes[position])
    }
}

class QuoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val idTextView = itemView.idTextView
    private val timeTextView = itemView.timeTextView
    private val ratingTextView = itemView.ratingTextView
    private val descriptionTextView = itemView.descriptionTextView

    fun bindQuote(quote: Quote) {
        idTextView.text = quote.id.toString()
        timeTextView.text = quote.time
        ratingTextView.text = quote.rating.toString()
        descriptionTextView.text = quote.description
    }
}