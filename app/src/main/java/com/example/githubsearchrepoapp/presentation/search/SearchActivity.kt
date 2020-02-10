package com.example.githubsearchrepoapp.presentation.search

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.githubsearchrepoapp.R
import com.example.githubsearchrepoapp.domain.search.model.RepoModel
import com.example.githubsearchrepoapp.domain.search.model.network.Status
import com.example.githubsearchrepoapp.presentation.search.adapter.SearchResultListAdapter
import kotlinx.android.synthetic.main.activity_search.*
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
        adapter = SearchResultListAdapter({ viewModel.deleteRepo(it) },
            { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it))) })

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
        list.adapter = adapter
        viewModel.reposLiveData.observe(this, Observer<PagedList<RepoModel>> {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            when (it.status) {
                Status.RUNNING -> progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> progressBar.visibility = View.GONE
                Status.FAILED -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                }
            }
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
}
