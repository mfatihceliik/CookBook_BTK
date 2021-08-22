package com.muhammedfatihcelik.foodsbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.muhammedfatihcelik.foodsbook.R
import com.muhammedfatihcelik.foodsbook.util.imageDownload
import com.muhammedfatihcelik.foodsbook.util.makePlaceHolder
import com.muhammedfatihcelik.foodsbook.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_foods_detail.*

class FoodsDetailFragment : Fragment() {

    private lateinit var viewModel: FoodDetailViewModel

    private var food_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_foods_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            food_id = FoodsDetailFragmentArgs.fromBundle(it).foodId
        }

        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.getRoomData(food_id)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer { food ->
            food?.let {
                food_detail_food_name.text = it.food_name
                food_detail_food_calorie.text = it.food_calorie
                food_detail_food_fat.text = it.food_fat
                food_detail_food_protein.text = it.food_protein
                food_detail_food_carbohydrate.text = it.food_carbohydrate
                context?.let {
                    food_detail_imageView.imageDownload(food.food_image, makePlaceHolder(it))
                }
            }
        })
    }

}