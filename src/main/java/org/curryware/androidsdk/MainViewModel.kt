package org.curryware.androidsdk

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.curryware.androidsdk.model.Posts
import org.curryware.androidsdk.repository.Repository
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val restResponse: MutableLiveData<Response<Posts>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            restResponse.value =response
        }
    }
}