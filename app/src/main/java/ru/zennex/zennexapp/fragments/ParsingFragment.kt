package ru.zennex.zennexapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.model.Quote
import ru.zennex.zennexapp.repositories.QuotesRepositoryProvider
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_parsing.*
import ru.zennex.zennexapp.adapters.QuotesAdapter

class ParsingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parsing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadQuotes()
    }

    @SuppressLint("CheckResult")
    private fun loadQuotes(): List<Quote>? {
        progressBar.visibility = View.VISIBLE
        val quotesRepository = QuotesRepositoryProvider.provideQuotesRepository()
        var responseQuotes: List<Quote>? = null
        quotesRepository.uploadQuotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                responseQuotes = response.quotes
                },
                { error ->
                    error.printStackTrace()
                    Log.d("debug", "error with loading Quotes")
                },
                {
                    progressBar.visibility = View.INVISIBLE
                    parsingRecycler?.adapter = responseQuotes?.let { QuotesAdapter(it) }
                })
        return responseQuotes
    }
}