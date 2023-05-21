package com.tugrulkara.cryptocurrencymvvm.view.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.tugrulkara.cryptocurrencymvvm.databinding.FragmentDetailsBinding
import com.tugrulkara.cryptocurrencymvvm.view.util.downloadFromUrl
import com.tugrulkara.cryptocurrencymvvm.view.util.placeholderProgressBar
import com.tugrulkara.cryptocurrencymvvm.view.viewmodel.DetailsViewModel
import com.tugrulkara.cryptocurrencymvvm.view.viewmodel.FeedViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel:DetailsViewModel

    private var uuid= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uuid=DetailsFragmentArgs.fromBundle(it).uuid
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.getCryptoCurrency(uuid)

        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.cryptoCurrencyViewModel.observe(viewLifecycleOwner, Observer { cryptoCurrency->

            cryptoCurrency?.let {currency->

                binding.currencyNameText.text=currency.currencyName
                binding.currencyPriceText.text=currency.currencyPrice
                binding.currencySymbolText.text=currency.currencySymbol
                binding.currencyVolumeText.text=currency.currencyTotalVolume

                context?.let {
                    binding.currencyImage.downloadFromUrl(currency.currencyImage,
                        placeholderProgressBar(it))
                }


            }
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}