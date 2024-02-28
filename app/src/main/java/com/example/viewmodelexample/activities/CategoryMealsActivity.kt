package com.example.viewmodelexample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.viewmodelexample.adapter.CategoryMealsAdapter
import com.example.viewmodelexample.databinding.ActivityCategoryMealsBinding
import com.example.viewmodelexample.fragment.HomeFragment
import com.example.viewmodelexample.pojo.Category
import com.example.viewmodelexample.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    private var categoriesList = ArrayList<Category>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareMealsRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsViewModel.observerMealsLiveData().observe(this, Observer { mealsList ->

         //binding.tvHome.text = mealsList.size.toString()

           //binding.tvHome.text = categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
             //  .toString()

            categoryMealsAdapter.setMealsList(mealsList)
        })

    }

    private fun prepareMealsRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.recyclerViewMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }

    }
}