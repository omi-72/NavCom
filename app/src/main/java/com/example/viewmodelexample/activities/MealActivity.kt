package com.example.viewmodelexample.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.viewmodelexample.R
import com.example.viewmodelexample.databinding.ActivityMealBinding
import com.example.viewmodelexample.db.MealDatabase
import com.example.viewmodelexample.fragment.HomeFragment
import com.example.viewmodelexample.pojo.Meal
import com.example.viewmodelexample.viewmodel.MealViewModel
import com.example.viewmodelexample.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm : MealViewModel
    private lateinit var youTubeLink : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationView()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYouTubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.addToFavWhite.setOnClickListener {
           mealToSave?.let {
               mealMvvm.insertMeal(it)
               binding.addToFavWhite.visibility = View.INVISIBLE
               binding.addToFav.visibility = View.VISIBLE
               Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun onYouTubeImageClick() {
        binding.imageViewYouTube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave : Meal?=null
    private fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave = meal
                binding.textViewCategory.text = "Category : ${meal.strCategory}"
                binding.textViewArea.text = "Area : ${meal.strArea}"
                binding.textViewInstructionSteps.text = meal.strInstructions

                youTubeLink = meal.strYoutube.toString()
            }

        })
    }

    private fun setInformationView() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imageMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.addToFav.visibility = View.INVISIBLE
        binding.textViewInstruction.visibility = View.INVISIBLE
        binding.textViewCategory.visibility = View.INVISIBLE
        binding.textViewArea.visibility = View.INVISIBLE
        binding.imageViewYouTube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.addToFav.visibility = View.VISIBLE
        binding.textViewInstruction.visibility = View.VISIBLE
        binding.textViewCategory.visibility = View.VISIBLE
        binding.textViewArea.visibility = View.VISIBLE
        binding.imageViewYouTube.visibility = View.VISIBLE
    }
}