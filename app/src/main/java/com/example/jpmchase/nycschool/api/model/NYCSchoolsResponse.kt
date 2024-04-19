package com.example.jpmchase.nycschool.api.model

import com.google.gson.annotations.SerializedName

data class NYCSchoolsResponse(
    val dbn: String,
    @SerializedName("school_name") val schoolName: String,
    val boro: String,
    val location: String,
    @SerializedName("phone_number") val phoneNumber: String
)
