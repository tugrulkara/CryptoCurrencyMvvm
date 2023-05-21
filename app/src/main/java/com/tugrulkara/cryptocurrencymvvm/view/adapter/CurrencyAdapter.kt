package com.tugrulkara.cryptocurrencymvvm.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugrulkara.cryptocurrencymvvm.databinding.CurrencyListRowBinding
import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel
import com.tugrulkara.cryptocurrencymvvm.view.util.downloadFromUrl
import com.tugrulkara.cryptocurrencymvvm.view.util.placeholderProgressBar
import com.tugrulkara.cryptocurrencymvvm.view.view.FeedFragmentDirections

class CurrencyAdapter(val currencyList:ArrayList<CryptoModel>): RecyclerView.Adapter<CurrencyAdapter.RowHolder>() {

    class RowHolder(var itemBinding: CurrencyListRowBinding) : RecyclerView.ViewHolder(itemBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding = CurrencyListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)

    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemBinding.currencyPriceText.text=currencyList[position].currencyPrice
        holder.itemBinding.currencyNameText.text=currencyList[position].currencyName
        holder.itemBinding.currencyImage.downloadFromUrl(
            currencyList[position].currencyImage,
            placeholderProgressBar(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {
            val action= FeedFragmentDirections.actionFeedFragmentToDetailsFragment()
            action.uuid= currencyList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }

    }


    fun updateCurrencyList(updateList:List<CryptoModel>){

        currencyList.clear()
        currencyList.addAll(updateList)
        notifyDataSetChanged()
    }
}