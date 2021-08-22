package com.muhammedfatihcelik.foodsbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muhammedfatihcelik.foodsbook.R
import com.muhammedfatihcelik.foodsbook.model.Food
import com.muhammedfatihcelik.foodsbook.util.imageDownload
import com.muhammedfatihcelik.foodsbook.util.makePlaceHolder
import com.muhammedfatihcelik.foodsbook.view.FoodsListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

class FoodRecyclerAdapter(val foodList: ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(){

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_recycler_row, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.itemView.food_name.text = foodList.get(position).food_name
        holder.itemView.food_calorie.text = foodList.get(position).food_calorie
        holder.itemView.setOnClickListener {
            val action = FoodsListFragmentDirections.actionFoodsListFragmentToFoodsDetailFragment(foodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.imageDownload(foodList.get(position).food_image, makePlaceHolder(holder.itemView.context))
    }

    fun foodListUpdate(newFoodList: List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }
}