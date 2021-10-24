package dev.nairnei.stonewallet.model

import com.google.gson.annotations.SerializedName

/**
 * Model Response to OlindaApi
 *
 * This model is used to get Brita value from from json returned from Olinda.
 *
 */
data class OlindaResponse(
    @SerializedName("@odata.context")
    var odataContext: String,
    @SerializedName("value")
    var value: List<Value>
) {
    data class Value(
        @SerializedName("cotacaoCompra")
        var cotacaoCompra: Double,
        @SerializedName("cotacaoVenda")
        var cotacaoVenda: Double
    )
}