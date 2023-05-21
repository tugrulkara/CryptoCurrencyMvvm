package com.tugrulkara.cryptocurrencymvvm.view.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel
import com.tugrulkara.cryptocurrencymvvm.view.service.CurrencyDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application):BaseViewModel(application){

    val cryptoCurrencyViewModel = MutableLiveData<CryptoModel>()

    fun getCryptoCurrency(uuid: Int){

        launch {
            cryptoCurrencyViewModel.value = CurrencyDatabase(getApplication()).dao().getCryptoCurrency(uuid)
        }
    }


}