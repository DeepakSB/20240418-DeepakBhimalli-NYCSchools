package com.example.jpmchase.nycschool.api.model

import com.google.gson.annotations.SerializedName

data class NYCSchoolsSATScoreResponse (
    val dbn: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("num_of_sat_test_takers") val satNumTestsTakers: String,
    @SerializedName("sat_critical_reading_avg_score") val readingAvgScore: String,
    @SerializedName("sat_math_avg_score") val mathAvgScore: String,
    @SerializedName("sat_writing_avg_score") val writingAvgScore: String
)