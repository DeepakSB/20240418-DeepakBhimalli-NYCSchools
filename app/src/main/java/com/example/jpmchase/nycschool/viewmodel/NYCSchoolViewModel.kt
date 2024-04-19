package com.example.jpmchase.nycschool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmchase.nycschool.api.data.NYCSchoolRepository
import com.example.jpmchase.nycschool.api.model.NYCSchoolsData
import com.example.jpmchase.nycschool.api.model.NYCSchoolsSATScoreData
import com.example.jpmchase.nycschool.api.network.SchoolsResultState
import com.example.jpmchase.nycschool.api.network.SchoolsSATResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NYCSchoolViewModel @Inject internal constructor (
    private val nycSchoolRepository: NYCSchoolRepository
) : ViewModel() {

    private val _nycSchoolsData = MutableStateFlow(listOf<NYCSchoolsData>())
    val nycSchoolsData: MutableStateFlow<List<NYCSchoolsData>> = _nycSchoolsData
    private val _satScoreData = MutableStateFlow(listOf<NYCSchoolsSATScoreData>())
    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> = _loading

    init {
        getNycSchools()
    }

    private fun getNycSchools() {
        _loading.value = true
        viewModelScope.launch (Dispatchers.IO) {
            val result = nycSchoolRepository.getNycSchools()
            when (result) {
                is SchoolsResultState.Success -> {
                    _loading.value = false
                    nycSchoolRepository.mapSchoolsData(result.nycSchools)
                    _nycSchoolsData.value = nycSchoolRepository.getNycSchoolsData()
                }
                is SchoolsResultState.Loading -> {
                    _loading.value = true
                }
                is SchoolsResultState.Error -> {

                }
            }
            getNycSchoolsSATScores()
        }
    }

    private fun getNycSchoolsSATScores() {
        viewModelScope.launch (Dispatchers.IO) {
            val result = nycSchoolRepository.getSchoolsSATScores()
            when (result) {
                is SchoolsSATResultState.Success -> {
                    nycSchoolRepository.mapSchoolsSATScores(result.nycSchoolsSATScores)
                    _satScoreData.value = nycSchoolRepository.getNycSchoolsSATData()
                }
                is SchoolsSATResultState.Loading -> {
                    _loading.value = true
                }
                is SchoolsSATResultState.Error -> {

                }
            }
        }
    }

    fun filterNYCSchoolSATScore(dbn : String) : List<NYCSchoolsSATScoreData> {
        var data = listOf<NYCSchoolsSATScoreData>()
        if (_satScoreData.value.isNotEmpty()) {
            data = _satScoreData.value.filter {
                it.dbn == dbn
            }
        }
        return data
    }
}