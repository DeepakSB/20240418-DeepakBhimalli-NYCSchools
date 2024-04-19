package com.example.jpmchase.nycschool.api.data

import com.example.jpmchase.nycschool.api.model.NYCSchoolsData
import com.example.jpmchase.nycschool.api.model.NYCSchoolsResponse
import com.example.jpmchase.nycschool.api.model.NYCSchoolsSATScoreData
import com.example.jpmchase.nycschool.api.model.NYCSchoolsSATScoreResponse
import com.example.jpmchase.nycschool.api.network.ApiService
import com.example.jpmchase.nycschool.api.network.SchoolsResultState
import com.example.jpmchase.nycschool.api.network.SchoolsSATResultState

interface NYCSchoolRepository {
    suspend fun getNycSchools() : SchoolsResultState
    suspend fun getSchoolsSATScores() : SchoolsSATResultState
    fun mapSchoolsData(nycSchoolsResponse: List<NYCSchoolsResponse>)
    fun getNycSchoolsData() : List<NYCSchoolsData>
    fun mapSchoolsSATScores(nycSchoolsSATScoreResponse: List<NYCSchoolsSATScoreResponse>)
    fun getNycSchoolsSATData() : List<NYCSchoolsSATScoreData>
}

class NYCSchoolRepositoryImpl(private val apiService: ApiService) : NYCSchoolRepository {

    private val nycSchoolsData = arrayListOf<NYCSchoolsData>()
    private val nycSchoolsSATScoreData = arrayListOf<NYCSchoolsSATScoreData>()

    override suspend fun getNycSchools() : SchoolsResultState {
        try {
            val response = apiService.getNycSchools()
            return SchoolsResultState.Success(response)
        } catch (e : Exception) {
            e.message.toString()
            return SchoolsResultState.Error("Error")
        }
    }

    override fun mapSchoolsData(nycSchoolsResponse: List<NYCSchoolsResponse>) {
        var i = 0
        val schoolsData = nycSchoolsResponse.sortedBy { it.schoolName }
        schoolsData.forEach {
            nycSchoolsData.add(NYCSchoolsData())
            nycSchoolsData[i].dbn = schoolsData[i].dbn
            nycSchoolsData[i].schoolName = schoolsData[i].schoolName
            nycSchoolsData[i].boro = schoolsData[i].boro
            nycSchoolsData[i].location = schoolsData[i].location
            nycSchoolsData[i].phoneNumber = schoolsData[i].phoneNumber
            i++
        }
    }

    override suspend fun getSchoolsSATScores(): SchoolsSATResultState {
        try {
            val response = apiService.getNycSchoolsSATScores()
            return SchoolsSATResultState.Success(response)
        } catch (e : Exception) {
            e.message.toString()
        }
        return SchoolsSATResultState.Error("Error")
    }

    override fun mapSchoolsSATScores(nycSchoolsSATScoreResponse: List<NYCSchoolsSATScoreResponse>) {
        var i = 0
        nycSchoolsSATScoreResponse.forEach {
            nycSchoolsSATScoreData.add(NYCSchoolsSATScoreData())
            nycSchoolsSATScoreData[i].dbn = nycSchoolsSATScoreResponse[i].dbn
            nycSchoolsSATScoreData[i].schoolName = nycSchoolsSATScoreResponse[i].schoolName
            nycSchoolsSATScoreData[i].numSatTestsTakers = nycSchoolsSATScoreResponse[i].satNumTestsTakers
            nycSchoolsSATScoreData[i].satReadingAvgScore = nycSchoolsSATScoreResponse[i].readingAvgScore
            nycSchoolsSATScoreData[i].satMathAvgScore = nycSchoolsSATScoreResponse[i].mathAvgScore
            nycSchoolsSATScoreData[i].satWritingAvgScore = nycSchoolsSATScoreResponse[i].writingAvgScore
            i++
        }
    }

    override fun getNycSchoolsData(): List<NYCSchoolsData> {
        return nycSchoolsData
    }

    override fun getNycSchoolsSATData(): List<NYCSchoolsSATScoreData> {
        return nycSchoolsSATScoreData
    }
}