package com.example.jpmchase.nycschool.api

import com.example.jpmchase.nycschool.api.data.NYCSchoolRepository

interface AppServiceModelFactory<T> {
    fun create() : T
}

class AppServiceModelFactoryImpl (private val nycSchoolRepository: NYCSchoolRepository)