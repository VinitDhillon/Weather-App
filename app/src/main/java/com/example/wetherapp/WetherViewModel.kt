package com.example.wetherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wetherapp.api.Constant
import com.example.wetherapp.api.NetworkResponse
import com.example.wetherapp.api.NetworkResponse.*
import com.example.wetherapp.api.RetrofitInstance
import com.example.wetherapp.api.WetherModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class WetherViewModel: ViewModel() {
    private val wetherApi = RetrofitInstance.wetherApi
    private val _wetherResult = MutableLiveData<NetworkResponse<WetherModel>>()
    val weatherResult: LiveData<NetworkResponse<WetherModel>> = _wetherResult
    fun getData(city: String){
        viewModelScope.launch {
            try{
           val response = wetherApi.getWether(Constant.apiKey, city)
            if(response.isSuccessful){
              response.body()?.let {
                  _wetherResult.value = NetworkResponse.Success(it)
              }
            }else {
                _wetherResult.value = NetworkResponse.Error("Failed to load data")
            }

            }catch (e: Exception){
                _wetherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }
}