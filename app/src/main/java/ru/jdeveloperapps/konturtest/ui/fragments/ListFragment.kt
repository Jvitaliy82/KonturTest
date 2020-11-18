package ru.jdeveloperapps.konturtest.ui.fragments

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import ru.jdeveloperapps.konturtest.R
import ru.jdeveloperapps.konturtest.adapters.UsersAdapter
import ru.jdeveloperapps.konturtest.other.Resourse
import ru.jdeveloperapps.konturtest.viewModels.MainViewModel

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    lateinit var viewModel: MainViewModel
    private val mAdapter = UsersAdapter()

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recyclerView.adapter = mAdapter
        viewModel.localData.observe(viewLifecycleOwner, {
            mAdapter.differ.submitList(it)
        })

        viewModel.stateData.observe(viewLifecycleOwner, {
            when (it) {
                is Resourse.Success -> progressBar.visibility = View.GONE
                is Resourse.Loading -> progressBar.visibility = View.VISIBLE
                is Resourse.Error -> {}
            }
        })

        viewModel.updateData()
    }
}