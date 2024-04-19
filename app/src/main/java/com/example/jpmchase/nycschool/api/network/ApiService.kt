package com.example.jpmchase.nycschool.api.network

import com.example.jpmchase.nycschool.api.model.NYCSchoolsResponse
import com.example.jpmchase.nycschool.api.model.NYCSchoolsSATScoreResponse
import com.example.jpmchase.nycschool.utils.Constants.API_NYC_SCHOOLS_LIST
import com.example.jpmchase.nycschool.utils.Constants.API_NYC_SCHOOLS_SAT_SCORES
import retrofit2.http.GET

interface ApiService {

    @GET(API_NYC_SCHOOLS_LIST)
    suspend fun getNycSchools() : List<NYCSchoolsResponse>

    @GET(API_NYC_SCHOOLS_SAT_SCORES)
    suspend fun getNycSchoolsSATScores() : List<NYCSchoolsSATScoreResponse>

}