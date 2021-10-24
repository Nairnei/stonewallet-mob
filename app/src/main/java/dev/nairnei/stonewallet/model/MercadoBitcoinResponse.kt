package dev.nairnei.stonewallet.model

import com.google.gson.annotations.SerializedName

/**
 * Model Response to MercadoBitcoinApi
 *
 * This model is used to get bitcoin value from from json return from MercadoBitcoin.
 *
 */
data class MercadoBitcoinResponse(
    @SerializedName("ticker")
    var ticker: Ticker
) {
    data class Ticker(
        @SerializedName("buy")
        var buy: String,
        @SerializedName("date")
        var date: Int,
        @SerializedName("high")
        var high: String,
        @SerializedName("last")
        var last: String,
        @SerializedName("low")
        var low: String,
        @SerializedName("open")
        var `open`: String,
        @SerializedName("sell")
        var sell: String,
        @SerializedName("vol")
        var vol: String
    )
}