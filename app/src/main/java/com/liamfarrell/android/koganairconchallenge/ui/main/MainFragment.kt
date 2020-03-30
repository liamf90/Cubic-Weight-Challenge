package com.liamfarrell.android.koganairconchallenge.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.liamfarrell.android.koganairconchallenge.R
import com.liamfarrell.android.koganairconchallenge.api.KoganProductApi
import com.liamfarrell.android.koganairconchallenge.util.CubicWeightCalculator
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        subscribeUi(viewModel)
    }

    private fun subscribeUi(viewModel: MainViewModel){
        startButton.setOnClickListener {
            it.isEnabled = false
            viewModel.start()
        }

        viewModel.averageCubicWeight.observe(viewLifecycleOwner, Observer {
            it?.let{averageCubicWeightTextView.text = String.format("%.3f %s", it, getString(R.string.kg))}
        })

        viewModel.productsCount.observe(viewLifecycleOwner, Observer {
            it?.let {productsFoundTextView.text = it.toString()}
        })
    }


}
