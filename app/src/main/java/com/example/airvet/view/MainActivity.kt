package com.example.airvet.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.airvet.databinding.ActivityMainBinding
import com.example.airvet.repository.UsersRepository
import com.example.airvet.retrofit.RetrofitService
import com.example.airvet.view.adapter.UsersAdapter
import com.example.airvet.viewmodel.UsersViewModel
import com.example.airvet.viewmodel.UsersViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: UsersViewModel
    lateinit var adapter: UsersAdapter
     var userDisplayedItems= 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initScrollListener()
        val retrofitService = RetrofitService.getInstance()
        val movieRepository = retrofitService?.let { UsersRepository(it) }

        viewModel = ViewModelProvider(
            this,
            UsersViewModelFactory(movieRepository)
        )[UsersViewModel::class.java]

        viewModel.movieList.observe(this , Observer {
            if(binding.rvList.adapter!=null){
                binding.rvList.scrollToPosition(binding.rvList.adapter!!.itemCount-1)
            }
            userDisplayedItems += 10
            binding.progressLoader.visibility = View.GONE
            binding.rvList.visibility = View.VISIBLE
            adapter = UsersAdapter(it)
            binding.rvList.adapter = adapter
        })
        viewModel.getAllUsers(userDisplayedItems)
    }

    fun initScrollListener() {
            binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (recyclerView.adapter as UsersAdapter).itemCount - 1) {
                            viewModel.getAllUsers(userDisplayedItems)
                        }
                    }
                }
            })


    }
}