package com.chethan.camerax.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(primaryKeys = ["id"])
data class Products(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("ean")
    val ean: String? = "",

    @field:SerializedName("icon")
    val icon: String? = "",

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("price")
    val price: String? = ""


) : Serializable {
}