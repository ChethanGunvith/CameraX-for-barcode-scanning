package com.chethan.camerax.model

import java.io.Serializable


data class OrderPayload(
    val timestamp: String? = "",
    val posid: String? = "",
    val vname: String? = "",
    val vid: String? = "",
    val tray: String? = "",
    val cons: List<ConsumptionPayload>?

) : Serializable {
}