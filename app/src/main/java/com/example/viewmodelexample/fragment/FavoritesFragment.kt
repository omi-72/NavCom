package com.example.viewmodelexample.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.snackbar.Snackbar
import androidx.activity.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelexample.adapter.MealsAdapter
import com.example.viewmodelexample.databinding.FragmentFavoritesBinding
import com.example.viewmodelexample.viewmodel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  viewModel = (activity as MainActivity).viewModel

        viewModel.getRandomMeal()
        viewModel.getPopularItems()
        viewModel.getCategories()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = FragmentFavoritesBinding.inflate(inflater)
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        prepareRecyclerView()
        observerFavorite()
        //initListener()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
               //viewModel.deleteMeal(favoritesMealsAdapter.differ.currentList[position])
                val deletedMeal=mealsAdapter.differ.currentList[position]
                viewModel.deleteMeal(deletedMeal)
                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                   "Undo",
                    View.OnClickListener {
                        viewModel.insertDeletedMeal(deletedMeal)
                  //    viewModel.insertMeal(favoritesMealsAdapter.differ.currentList[position])
                   }
             ).show()
           }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewFavorites)
    }

    private fun prepareRecyclerView() {
        mealsAdapter = MealsAdapter()
        binding.recyclerViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

    private fun observerFavorite() {
        viewModel.observerFavoritesMealsLiveData().observe(requireActivity(), Observer { meals ->
            mealsAdapter.differ.submitList(meals)

        })
    }


}