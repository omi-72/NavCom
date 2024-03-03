package com.example.viewmodelexample.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.example.viewmodelexample.R
import com.example.viewmodelexample.databinding.ActivityMainBinding
import com.example.viewmodelexample.db.MealDatabase
import com.example.viewmodelexample.viewmodel.HomeViewModel
import com.example.viewmodelexample.viewmodel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
//    val viewModel: HomeViewModel by lazy {
//        val mealDatabase = MealDatabase.getInstance(this)
//        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
//        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
//    }

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if(savedInstanceState == null) {

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
            navController = navHostFragment.navController

            // Setup the bottom navigation view with navController
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
            bottomNavigationView.setupWithNavController(navController)

            // Setup the ActionBar with navController and 3 top level destinations
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.home_fragment, R.id.favoritesFragment, R.id.catagoryFragment)
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.item_search_app ->{
//                val action = SearchFragmentDirection.actionGlobalSearchFragment()
//                navController.navigate(action)!!
//                return true
//            }
//            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}