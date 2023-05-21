package com.tugrulkara.cryptocurrencymvvm.view.service

import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyServiceAPI {

    //'https://api.coingecko.com/api/v3/coins/markets?vs_currency=USD&ids=bitcoin,ethereum,litecoin,solana,ripple,cardano,dogecoin,tron,stellar,chiliz,chainlink,iota'


    private val BASE_URL="https://api.coingecko.com/"

    private val api= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CurrencyAPI::class.java)

    fun getCurrencyData(): Single<List<CryptoModel>>{
        return api.getData()
    }


}