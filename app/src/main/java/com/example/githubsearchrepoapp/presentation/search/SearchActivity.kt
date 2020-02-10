package com.example.githubsearchrepoapp.presentation.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githubsearchrepoapp.R
import com.example.githubsearchrepoapp.domain.search.model.SearchResult
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()

    private lateinit var adapter: SearchResultListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initAdapter()
        initSearch()
    }

    private fun initAdapter() {
        adapter = SearchResultListAdapter()
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)

        list.adapter = adapter
        viewModel.reposLiveData.observe(this, Observer<PagedList<SearchResult>> {
           // showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })
    }

    private fun initSearch() {
        searchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchView.text.toString().trim().let {
                    if (it.isNotEmpty()) {
                        list.scrollToPosition(0)
                        viewModel.searchRepo(it)
                        adapter.submitList(null)
                    }
                }
            }
            true
        }

    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }
}
