package com.devendra.devendra_demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: MainApiRepository
) : ViewModel() {
    private val currentQuery = MutableLiveData("language:Kotlin")

    val repos = currentQuery.switchMap { queryString ->
        apiRepository.getApiData(queryString).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String) {
        currentQuery.value = query
    }

}