package org.curryware.androidsdk

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This holds all of the data that can be updated.  Uses the LiveData functionality to update
// information.  Trying to use this with coroutines.
class SdkViewModel : ViewModel() {

    val logTag = "SDKViewModel"

    val lastUpdateTime: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val loggedInUser: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val apiVersion: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val customSettings: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        Log.i(logTag, "Initializing Model View")
        lastUpdateTime.value = "Last Update Time"
        loggedInUser.value = "Logged in User"
    }
}