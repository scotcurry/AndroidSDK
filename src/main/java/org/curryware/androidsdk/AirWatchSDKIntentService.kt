package org.curryware.androidsdk

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.airwatch.sdk.AirWatchSDKBaseIntentService
import com.airwatch.sdk.profile.AnchorAppStatus
import com.airwatch.sdk.profile.ApplicationProfile
import com.airwatch.sdk.shareddevice.ClearReasonCode

class AirWatchSDKIntentService: AirWatchSDKBaseIntentService() {

    private val logTag: String = "AirWatchSDKIntentService"

    override fun onApplicationConfigurationChange(applicationConfiguration: Bundle) {
        Log.i(logTag, "onApplicationConfigurationChange Called")
    }

    override fun onApplicationProfileReceived(
        context: Context,
        profileId: String,
        appProfile: ApplicationProfile
    ) {
        Log.i(logTag, "onApplicationProfileReceived Called")
    }

    override fun onClearAppDataCommandReceived(context: Context, reasonCode: ClearReasonCode) {
        Log.i(logTag, "onClearAppDataCommand Called")
    }

    override fun onAnchorAppStatusReceived(context: Context, appStatus: AnchorAppStatus) {
        Log.i(logTag, "onAnchorAppStatusReceived Called")
    }

    override fun onAnchorAppUpgrade(context: Context, isUpgrade: Boolean) {
        Log.i(logTag, "onAnchorAppUpgrade Called")
    }
}