package com.tugrulkara.cryptocurrencymvvm.view.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("crypto_currency")
data class CryptoModel(

    @ColumnInfo("name")
    @SerializedName("name")
    val currencyName:String?,
    @ColumnInfo("image")
    @SerializedName("image")
    val currencyImage: String?,
    @ColumnInfo("current_price")
    @SerializedName("current_price")
    val currencyPrice: String?,
    @ColumnInfo("symbol")
    @SerializedName("symbol")
    val currencySymbol: String?,
    @ColumnInfo("total_volume")
    @SerializedName("total_volume")
    val currencyTotalVolume: String?){

    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0

}