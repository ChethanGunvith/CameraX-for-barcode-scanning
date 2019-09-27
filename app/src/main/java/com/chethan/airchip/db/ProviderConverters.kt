package com.chethan.airchip.db

import androidx.room.TypeConverter
import com.chethan.airchip.model.ConsumptionPayload
import com.google.gson.Gson


object ProviderConverters {
    @TypeConverter
    @JvmStatic
    fun stringToConsumptionList(data: String?): List<ConsumptionPayload>? {

        var list = ArrayList<ConsumptionPayload>()

        data?.let {
            it.split(",").map { value ->
                val item = Gson().fromJson(value, ConsumptionPayload::class.java)
                list.add(item)
            }
        }

        return list
    }

    @TypeConverter
    @JvmStatic
    fun consumptionListToString(list: List<ConsumptionPayload>?): String? {
        return list?.joinToString(",")
    }
}
