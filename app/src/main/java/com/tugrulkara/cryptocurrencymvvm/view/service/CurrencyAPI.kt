package com.tugrulkara.cryptocurrencymvvm.view.service

import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyAPI {


    @GET("api/v3/coins/markets?vs_currency=USD&ids=bitcoin,ethereum,litecoin,solana,ripple,cardano,dogecoin,tron,stellar,chiliz,chainlink,iota")
    fun getData(): Single<List<CryptoModel>>

}