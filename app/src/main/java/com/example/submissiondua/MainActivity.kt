package com.example.submissiondua

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiondua.response.ItemsItem
import com.example.submissiondua.databinding.ActivityMainBinding
import com.example.submissiondua.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listUserAdapter = ListUserAdapter()

    companion object {
        private const val TAG = "MainActivity"
        private const val q = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUser.adapter = listUserAdapter

        supportActionBar?.title = "Cari User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val mainActivityViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                MainActivityViewModel::class.java
            )


        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = binding.svUser
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.cari_user)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                Toast.makeText(this@MainActivity, q, Toast.LENGTH_SHORT).show()
                mainActivityViewModel.getListUser(q)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        mainActivityViewModel.items.observe(this) { items -> setUserResponse(items) }

        mainActivityViewModel.isLoading.observe(this) { showLoading(it) }

    }

    private fun setUserResponse(items: List<ItemsItem>) {
        listUserAdapter.setData(items)
        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: ItemsItem) {
                showSelectedUser(user)
                val moveWithObjectIntent = Intent(this@MainActivity, DetailUser::class.java)
                moveWithObjectIntent.putExtra(DetailUser.USERNAME, user.login)
                startActivity(moveWithObjectIntent)
            }
        })
    }

    private fun showSelectedUser(user: ItemsItem) {
        Toast.makeText(this, "Anda memilih " + user.login, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}


