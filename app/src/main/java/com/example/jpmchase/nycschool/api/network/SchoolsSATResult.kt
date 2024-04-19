package com.example.jpmchase.nycschool.api.network

import com.example.jpmchase.nycschool.api.model.NYCSchoolsSATScoreResponse

sealed interface SchoolsSATResultState {
    data class Success(val nycSchoolsSATScores: List<NYCSchoolsSATScoreResponse>) : SchoolsSATResultState

    data class Error (val errorMsg: String) : SchoolsSATResultState

    data class Loading (val loadingMsg: String) : SchoolsSATResultState
}