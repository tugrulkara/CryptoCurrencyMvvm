package com.tugrulkara.cryptocurrencymvvm.view.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel
import com.tugrulkara.cryptocurrencymvvm.view.service.CurrencyDatabase
import com.tugrulkara.cryptocurrencymvvm.view.service.CurrencyServiceAPI
import com.tugrulkara.cryptocurrencymvvm.view.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application): BaseViewModel(application) {

    private val api=CurrencyServiceAPI()
    private val compositDisposable=CompositeDisposable()

    private val customSharedPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime = 10*60*1000*1000*1000L

    val currencies = MutableLiveData<List<CryptoModel>>()
    val error= MutableLiveData<Boolean>()
    val progress=MutableLiveData<Boolean>()

    fun getData(){

        val updateTime = customSharedPreferences.getTime()
        if (updateTime!=null && updateTime!=0L && System.nanoTime()-updateTime<refreshTime){
            getDataFromSqlite()
        }else{
            getDataFromApi()
        }

    }

    fun refreshFromAPI(){
        getDataFromApi()
    }


    private fun getDataFromApi(){

        progress.value=true

        compositDisposable.add(
            api.getCurrencyData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CryptoModel>>(), Disposable{
                    override fun onSuccess(t: List<CryptoModel>) {
                        storeInSqlite(t)
                        Toast.makeText(getApplication(),"API",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        error.value=true
                        progress.value=false
                    }

                })

        )

    }

    private fun storeInSqlite(currencyList: List<CryptoModel>){

        launch {

            val dao=CurrencyDatabase(getApplication()).dao()
            dao.deleteAllCryptoCurrency()
            val listLong=dao.insertAll(*currencyList.toTypedArray())
            var i = 0

            while (i<currencyList.size){
                currencyList[i].uuid=listLong[i].toInt()
                i++
            }

            showCurrencies(currencyList)

        }
        customSharedPreferences.saveTime(System.nanoTime())
    }

    private fun showCurrencies(currencyList: List<CryptoModel>){

        currencies.value=currencyList
        error.value=false
        progress.value=false
    }

    private fun getDataFromSqlite(){

        progress.value=true
        launch {
            val dao=CurrencyDatabase(getApplication()).dao().getAllCryptoCurrency()
            showCurrencies(dao)
            Toast.makeText(getApplication(),"Sqlite", Toast.LENGTH_SHORT).show()

        }
    }


}