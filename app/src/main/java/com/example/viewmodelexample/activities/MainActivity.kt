package com.example.viewmodelexample.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}

      /*  if(savedInstanceState == null){
            setUpBottomNav()
        }else{
            Log.d("Hasan", "No instance")
        }



//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//
//        setupActionBarWithNavController(navController)
//
////        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
////        val navController = Navigation.findNavController(this, R.id.host_fragment)
////
////        NavigationUI.setupWithNavController(bottomNavigationView,navController)
//
//        binding.btmNav.setupWithNavController(navController)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val graphIds = listOf(R.navigation.home_nav_graph,R.navigation.favorite_nav_graph,R.navigation.category_nav_graph)
        val bottomNav = findViewById<BottomNavigationView>(R.id.btm_nav)
        val controller = bottomNav.setupWithNavController(
            graphIds,
            supportFragmentManager,
            R.id.host_fragment,
            intent
        )
        controller.observe(this){
            setupActionBarWithNavController(it)
        }
        navController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.value?.navigateUp()!! || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
*//*
        return when(item.itemId){
            R.id.item_search_app ->{
                val action = SearchFragmentDirections.actionGlobalSearchFragment()
                navController?.value?.navigate(action)!!
                return true
            }
            else -> item.onNavDestinationSelected(navController?.value!!) || super.onOptionsItemSelected(item)
        }*//*
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


}*/