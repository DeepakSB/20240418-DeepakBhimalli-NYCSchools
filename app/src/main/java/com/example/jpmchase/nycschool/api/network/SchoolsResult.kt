package com.example.jpmchase.nycschool.api.network

import com.example.jpmchase.nycschool.api.model.NYCSchoolsResponse

sealed interface SchoolsResultState {
    data class Success(val nycSchools: List<NYCSchoolsResponse>) : SchoolsResultState

    data class Error (val errorMsg: String) : SchoolsResultState

    data class Loading (val loadingMsg: String) : SchoolsResultState
}