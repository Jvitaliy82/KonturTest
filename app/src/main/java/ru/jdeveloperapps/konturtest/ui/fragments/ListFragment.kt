package ru.jdeveloperapps.konturtest.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
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

        swipe_container.setOnRefreshListener {
            viewModel.updateData()
        }

        recyclerView.adapter = mAdapter
        mAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("user", it)
            }
            findNavController().navigate(
                R.id.action_listFragment_to_detailFragment,
                bundle
            )
        }
        viewModel.localData.observe(viewLifecycleOwner, {
            mAdapter.differ.submitList(it)
        })

        viewModel.stateData.observe(viewLifecycleOwner, {
            when (it) {
                is Resourse.Success -> {
                    progressBar.visibility = View.GONE
                    swipe_container.isRefreshing = false
                }
                is Resourse.Loading -> progressBar.visibility = View.VISIBLE
                is Resourse.Error -> {}
            }
        })
    }
}