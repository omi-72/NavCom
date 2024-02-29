package com.example.viewmodelexample.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewmodelexample.activities.CategoryMealsActivity
import com.example.viewmodelexample.activities.MainActivity
import com.example.viewmodelexample.activities.MealActivity
import com.example.viewmodelexample.adapter.CategoriesAdapter
import com.example.viewmodelexample.adapter.MostPopularAdapter
import com.example.viewmodelexample.bottomsheet.MealBottomSheetFragment
import com.example.viewmodelexample.databinding.FragmentHomeBinding
import com.example.viewmodelexample.db.MealDatabase
import com.example.viewmodelexample.pojo.Meal
import com.example.viewmodelexample.pojo.MealsByCategory
import com.example.viewmodelexample.viewmodel.HomeViewModel
import com.example.viewmodelexample.viewmodel.HomeViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rvMyList: RecyclerView
    private lateinit var viewModel : HomeViewModel

//    private val viewModel: HomeViewModel by lazy {
//        val mealDatabase = MealDatabase.getInstance(requireActivity())
//        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
//        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
//    }


    //val viewModel: HomeViewModel by viewModels()


    companion object{
        const val MEAL_ID = "com.example.viewmodelexample.fragment.idMeal"
        const val MEAL_NAME = "com.example.viewmodelexample.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.viewmodelexample.fragment.thumbMeal"
        const val CATEGORY_NAME = "com.example.viewmodelexample.fragment.categoryName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        viewModel = (activity as MainActivity).viewModel

   //    viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

//        val viewModelProvider:ViewModelProvider= ViewModelProviders.of(this)
//        viewModel= viewModelProvider.get(HomeViewModel::class.java)

       // viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance((requireView()))).get(HomeViewModel::class.java)

        popularItemsAdapter = MostPopularAdapter()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        preparePopularItemsRecyclerView()

      //  viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemsClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()

        onPopularItemsLongClick()

      //  onSearchIconClick()

    }


    private fun onPopularItemsLongClick() {
        popularItemsAdapter.onLongItemClick = { meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")

        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.observerCategoriesLiveData().observe(viewLifecycleOwner) { categories ->

                categoriesAdapter.setCategoryList(categories)
               // Log.d("test", category.strCategory)
            }
    }

    private fun onPopularItemsClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recyclerViewOverPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter

        }
    }

    private fun observerPopularItemsLiveData() {
        viewModel.observerPopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imageRandomMeal)
            this.randomMeal = meal

        }
    }

}