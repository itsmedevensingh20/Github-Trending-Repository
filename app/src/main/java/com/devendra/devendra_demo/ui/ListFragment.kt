package com.devendra.devendra_demo.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.devendra.devendra_demo.MainViewModel
import com.devendra.devendra_demo.R
import com.devendra.devendra_demo.databinding.FragmentListBinding
import com.devendra.devendra_demo.response.Languages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setHasOptionsMenu(true)
    }

    var adp = ListAdapter()

    private fun initAdapter() {
        recyclerList.adapter = adp

        binding.apply {
            btnRetry.setOnClickListener {
                adp.retry()
            }
        }

        viewModel.repos.observe((activity as MainActivity)) {
            adp.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adp.addLoadStateListener { loadState ->
            binding.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                error.isVisible = loadState.source.refresh is LoadState.Error

                recyclerList.isVisible = !(loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adp.itemCount < 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_repos, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        val searchAutoComplete: SearchView.SearchAutoComplete =
            searchView.findViewById(androidx.appcompat.R.id.search_src_text)

        searchAutoComplete.setTextColor(Color.WHITE)
        searchAutoComplete.setDropDownBackgroundResource(R.color.teal_700)

        val listAdapter: ArrayAdapter<String> = ArrayAdapter(
            this.requireContext(),
            R.layout.dropdown_item,
            Languages.data
        )
        searchAutoComplete.setAdapter(listAdapter)
        searchAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, itemIndex, _ ->
                val queryString = adapterView.getItemAtPosition(itemIndex) as String
                searchAutoComplete.setText(
                    String.format(
                        "%s",
                        queryString
                    )
                )
                binding?.recyclerList?.scrollToPosition(0)
                val languageQuery = String.format(getString(R.string.query), queryString)
                viewModel.searchRepos(languageQuery)
                searchView.clearFocus()
            }
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}