package com.tugrulkara.cryptocurrencymvvm.view.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tugrulkara.cryptocurrencymvvm.view.model.CryptoModel

@Database(entities = arrayOf(CryptoModel::class), version = 1)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun dao():CurrencyDAO

    companion object{

        @Volatile var instance: CurrencyDatabase?=null

        private var lock= Any()

        operator fun invoke(context: Context)= instance?: synchronized(lock){

            instance ?: makeDatabase(context).also {
                instance= it
            }
        }

        private fun makeDatabase(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            CurrencyDatabase::class.java,"currencydatabase")
            .build()

    }

}