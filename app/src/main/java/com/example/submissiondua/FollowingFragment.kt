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
import com.example.submissiondua.databinding.FragmentFollowingBinding
import com.example.submissiondua.response.FollowingResponseItem
import com.example.submissiondua.viewmodel.FollowingFragmentViewModel

class FollowingFragment() : Fragment() {

    companion object {
        @JvmStatic
        private val DATA_FRAGMENT = "username"
        fun newInstance(username: String?) =
            FollowingFragment().apply {
                val fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString(DATA_FRAGMENT, username)
                fragment.arguments = bundle
                return fragment
            }
    }

    private val listFollowingAdapter = ListFollowingAdapter()
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailUser.DATA_FRAGMENT).toString()
        binding.rvFollowing.adapter = listFollowingAdapter

        val layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFollowing.addItemDecoration(itemDecoration)

        val followingFragmentViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowingFragmentViewModel::class.java
            )

        followingFragmentViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setUserFollowing(
                listFollowing
            )
        }

        followingFragmentViewModel.isLoading.observe(viewLifecycleOwner, { showLoading(it) })


        Log.d("HASIL", username)
        followingFragmentViewModel.getListFollowing(username)
    }

    private fun setUserFollowing(listFollowing: List<FollowingResponseItem>) {
        listFollowingAdapter.setData(listFollowing)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
