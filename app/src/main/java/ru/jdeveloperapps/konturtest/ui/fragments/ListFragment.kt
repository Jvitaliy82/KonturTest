package ru.jdeveloperapps.konturtest.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import kotlinx.android.synthetic.main.fragment_list.*
import ru.jdeveloperapps.konturtest.R
import ru.jdeveloperapps.konturtest.adapters.UsersAdapter
import ru.jdeveloperapps.konturtest.other.Resourse
import ru.jdeveloperapps.konturtest.ui.MainActivity
import ru.jdeveloperapps.konturtest.viewModels.MainViewModel


@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    lateinit var viewModel: MainViewModel
    private val mAdapter = UsersAdapter()

    override fun onResume() {
        super.onResume()
        viewModel = (activity as MainActivity).viewModel

        setSwipeContainer()

        setRecyclerView()

        viewModel.localData.observe(viewLifecycleOwner, {
            mAdapter.submitList(it)
        })

        viewModel.stateData.observe(viewLifecycleOwner, { data ->
            when (data) {
                is Resourse.Success -> {
                    progressBar.visibility = View.GONE
                    swipe_container.isRefreshing = false
                }
                is Resourse.Loading -> {
                    swipe_container.isRefreshing = false
                    progressBar.visibility = View.VISIBLE
                }
                is Resourse.Error -> {
                    progressBar.visibility = View.GONE
                    swipe_container.isRefreshing = false
                    if (!data.content.hasBeenHandled)
                        Snackbar.make(
                            swipe_container,
                            data.content.getContentOrNull().toString(),
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                }
            }
        })

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        subscriber.onNext(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        subscriber.onNext(newText)
                    }
                    return false
                }

            })
        })
            .map { text -> text.trim() }
            .distinctUntilChanged()
            .subscribe({ text ->
                mAdapter.filter.filter(text)
            }, { e ->
                Log.d("TTT", "error: ${e.message}")
            }, {})
    }

    private fun setSwipeContainer() {
        swipe_container.setOnRefreshListener {
            viewModel.updateData()
        }

        searchView.setOnClickListener {
            (searchView as SearchView).onActionViewExpanded()
        }
    }

    private fun setRecyclerView() {
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        mAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("user", it)
            }
            findNavController().navigate(
                R.id.action_listFragment_to_detailFragment,
                bundle
            )
        }
    }
}