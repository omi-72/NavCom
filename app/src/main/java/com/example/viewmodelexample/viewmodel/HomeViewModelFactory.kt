package com.example.viewmodelexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodelexample.db.MealDatabase
import com.example.viewmodelexample.viewmodel.HomeViewModel

class HomeViewModelFactory(
   private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}