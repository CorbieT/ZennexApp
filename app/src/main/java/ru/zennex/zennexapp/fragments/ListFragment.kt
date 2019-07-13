package ru.zennex.zennexapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import ru.zennex.zennexapp.R
import ru.zennex.zennexapp.adapters.ListAdapter
import ru.zennex.zennexapp.viewModels.ListItemViewModel

class ListFragment : Fragment() {

    private lateinit var listItemViewModel: ListItemViewModel
    private var listItemsDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listItemViewModel = ViewModelProviders.of(this).get(ListItemViewModel::class.java)
        val recyclerAdapter = ListAdapter(mutableListOf(),
            Consumer { item -> listItemViewModel.update(item) },
            Consumer { item -> activity!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, EditListFragment(item))
                .commit() },
            Consumer { item -> listItemViewModel.delete(item) })
        listRecyclerView.adapter = recyclerAdapter

        listItemsDisposable = listItemViewModel.allListItems
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                recyclerAdapter.update(response.toMutableList())
                Log.d("debug", "response: ${response.size} items")
            },
                { error ->
                    error.printStackTrace()
                    Toast.makeText(activity!!, "error with loading ListItems from DB", Toast.LENGTH_LONG).show()
                })

        fab.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, EditListFragment())
                .commit()
        }
    }

    override fun onStop() {
        super.onStop()
        listItemsDisposable?.dispose()
    }
}