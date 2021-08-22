package com.muhammedfatihcelik.foodsbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.muhammedfatihcelik.foodsbook.R
import com.muhammedfatihcelik.foodsbook.adapter.FoodRecyclerAdapter
import com.muhammedfatihcelik.foodsbook.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_foods_list.*

class FoodsListFragment : Fragment() {

    private lateinit var viewModel: FoodListViewModel
    private val recyclerFoodAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foods_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        foodListRecycler.layoutManager = LinearLayoutManager(context)
        foodListRecycler.adapter = recyclerFoodAdapter


        swipRefreshLayout.setOnRefreshListener {
            food_list_loading.visibility = View.VISIBLE
            foodErrorMessage.visibility = View.GONE
            foodListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipRefreshLayout.isRefreshing = false
        }

        observeLiveData()

        arguments?.let {

        }

    }

    fun observeLiveData(){
        viewModel.foods.observe(viewLifecycleOwner, Observer { foods ->
            foods?.let {
                foodListRecycler.visibility = View.VISIBLE
                recyclerFoodAdapter.foodListUpdate(foods)
            }
        })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    foodListRecycler.visibility = View.GONE
                    foodErrorMessage.visibility = View.VISIBLE
                }else{
                    foodErrorMessage.visibility = View.GONE
                }
            }
        })

        viewModel.foodLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    foodListRecycler.visibility = View.GONE
                    foodErrorMessage.visibility = View.GONE
                    food_list_loading.visibility = View.VISIBLE
                }else{
                    food_list_loading.visibility = View.GONE
                }
            }
        })

    }

}