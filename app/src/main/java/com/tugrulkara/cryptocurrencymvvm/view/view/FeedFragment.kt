package com.tugrulkara.cryptocurrencymvvm.view.view

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tugrulkara.cryptocurrencymvvm.databinding.FragmentFeedBinding
import com.tugrulkara.cryptocurrencymvvm.view.adapter.CurrencyAdapter
import com.tugrulkara.cryptocurrencymvvm.view.viewmodel.FeedViewModel

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: FeedViewModel
    private val adapter= CurrencyAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.getData()

        binding.recyclerView.layoutManager= LinearLayoutManager(context)
        binding.recyclerView.adapter=adapter

        binding.swipeRefresh.setOnRefreshListener {
            binding.recyclerView.visibility=View.GONE
            viewModel.refreshFromAPI()
            binding.swipeRefresh.isRefreshing=false
        }

        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.currencies.observe(viewLifecycleOwner, Observer {countries->

            countries?.let {
                binding.recyclerView.visibility=View.VISIBLE
                adapter.updateCurrencyList(it)
            }

        })

        viewModel.progress.observe(viewLifecycleOwner, Observer{progress->

            progress?.let {
                if (it){
                    binding.recyclerView.visibility=View.GONE
                    binding.errorText.visibility=View.GONE
                    binding.progressBar.visibility=View.VISIBLE
                }else{
                    binding.errorText.visibility=View.GONE
                    binding.progressBar.visibility=View.GONE
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {error->

            error?.let {
                if (it){
                    binding.recyclerView.visibility=View.GONE
                    binding.errorText.visibility=View.VISIBLE
                    binding.progressBar.visibility=View.GONE
                }else{
                    binding.errorText.visibility=View.GONE
                    binding.progressBar.visibility=View.GONE
                }
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}