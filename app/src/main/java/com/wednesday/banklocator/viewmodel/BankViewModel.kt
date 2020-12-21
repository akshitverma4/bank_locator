package com.wednesday.banklocator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wednesday.banklocator.model.IfscResponse
import com.wednesday.banklocator.repository.BankDetailsRepository
import com.wednesday.banklocator.util.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class BankViewModel(val bankDetailsRepository: BankDetailsRepository
) : ViewModel() {

val ifscCodes: MutableLiveData<Resource<IfscResponse>> = MutableLiveData()

fun getIfscCode(IFSC: String) = viewModelScope.launch {
    ifscCodes.postValue(Resource.Loading())
    val response = bankDetailsRepository.getIfscCode(IFSC)
    ifscCodes.postValue(handleIfscCodeResponse(response))

}
fun upsert(item: IfscResponse) =
    GlobalScope.launch {
        bankDetailsRepository.upsert(item)
    }

fun delete(item: IfscResponse) = GlobalScope.launch {
    bankDetailsRepository.delete(item)
}

fun getAllShoppingItems() =  bankDetailsRepository.getAllBanks()

fun handleIfscCodeResponse(response: Response<IfscResponse>): Resource<IfscResponse> {
    if (response.isSuccessful) {
        response.body()?.let { resultResponse ->
            return Resource.Success(resultResponse)
        }
    }
    return Resource.Error(response.message())
}
}