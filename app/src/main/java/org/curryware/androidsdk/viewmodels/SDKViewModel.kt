package org.curryware.androidsdk.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.airwatch.sdk.profile.PasscodePolicy
import com.airwatch.sdk.profile.RestrictionPolicy

class SDKViewModel : ViewModel(){

    // This section is just to understand LiveData
    private val _currentName = MutableLiveData("Otis")
    val liveDataCurrentName: MutableLiveData<String> = _currentName

    private val _passcodePolicy = MutableLiveData<PasscodePolicy>()
    val passcodePolicy: MutableLiveData<PasscodePolicy> = _passcodePolicy

    private val _customSettingsPolicy = MutableLiveData<String>()
    val customSettingsPolicy: MutableLiveData<String> = _customSettingsPolicy

    private val _restrictionPolicy = MutableLiveData<RestrictionPolicy>()
    val restrictionPolicy: MutableLiveData<RestrictionPolicy> = _restrictionPolicy

    private val _apiVersion = MutableLiveData<Int>()
    val apiVersion: MutableLiveData<Int> = _apiVersion

    private val _isEnrolled = MutableLiveData<Boolean>()
    val isEnrolled: MutableLiveData<Boolean> = _isEnrolled
}