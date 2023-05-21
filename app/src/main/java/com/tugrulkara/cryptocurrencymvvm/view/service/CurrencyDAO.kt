package com.tugrulkara.cryptocurrencymvvm.view.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel

@Dao
interface CurrencyDAO {

    @Insert
    suspend fun insertAll(vararg cryptoModel: CryptoModel): List<Long>

    @Query("SELECT * FROM crypto_currency")
    suspend fun getAllCryptoCurrency(): List<CryptoModel>

    @Query("SELECT * FROM crypto_currency WHERE uuid = :uuid")
    suspend fun getCryptoCurrency(uuid: Int): CryptoModel

    @Query("DELETE FROM crypto_currency")
    suspend fun deleteAllCryptoCurrency()
}