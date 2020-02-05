package com.cyborg.a2z.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cyborg.a2z.MarsProperty
import com.cyborg.a2z.network.MarsApi
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    init {
        getDataFromTheInternet()
    }

    private fun getDataFromTheInternet() {
        coroutineScope.launch {
            // this will run on a thread managed by Retrofit
            var getPropertiesDeferred = MarsApi.retrofitService.getProperties()
            try {
                _properties.value = getPropertiesDeferred.await()
            } catch (e: Exception) {
                _properties.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
