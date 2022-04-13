package com.example.submissiondua

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiondua.response.FollowersResponseItem
import com.example.submissiondua.databinding.FragmentFollowersBinding
import com.example.submissiondua.databinding.FragmentFollowingBinding
import com.example.submissiondua.viewmodel.FollowersFragmentViewModel


class FollowersFragment() : Fragment() {

    companion object {
        @JvmStatic
        private val DATA_FRAGMENT = "username"
        fun newInstance(username: String?) =
            FollowersFragment().apply {
                val fragment = FollowersFragment()
                val bundle = Bundle()
                bundle.putString(DATA_FRAGMENT, username)
                fragment.arguments = bundle
                return fragment
            }
    }

    private val listFollowersAdapter = ListFollowersAdapter()
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollowers.adapter = listFollowersAdapter

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowers.addItemDecoration(itemDecoration)

        val followersFragmentViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowersFragmentViewModel::class.java
            )

        followersFragmentViewModel.listFollowers.observe(
            viewLifecycleOwner,
            { listFollowers -> setUserFollowers(listFollowers) })
        followersFragmentViewModel.isLoading.observe(viewLifecycleOwner, { showLoading(it) })
        followersFragmentViewModel.getListFollowers(
            arguments?.getString(DetailUser.DATA_FRAGMENT).toString()
        )
    }


    private fun setUserFollowers(listFollowers: List<FollowersResponseItem>) {
        listFollowersAdapter.setData(listFollowers)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
