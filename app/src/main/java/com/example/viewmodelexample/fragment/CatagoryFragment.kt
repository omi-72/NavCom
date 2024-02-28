package com.example.viewmodelexample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import com.example.viewmodelexample.adapter.CategoriesAdapter
import com.example.viewmodelexample.databinding.FragmentCatagoryBinding
import com.example.viewmodelexample.viewmodel.HomeViewModel


class CatagoryFragment : Fragment() {

    private lateinit var binding: FragmentCatagoryBinding
    private lateinit var categoriesAdapter : CategoriesAdapter


    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCatagoryBinding.inflate(inflater,container,false)

   //     viewModel = (activity as MainActivity).viewModel


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        prepareRecyclerView()
        observerCategories()
    }

    private fun observerCategories() {
        viewModel.observerCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList(categories)
        })
    }


    private fun prepareRecyclerView() {
       categoriesAdapter = CategoriesAdapter()
        binding.rcvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }


}