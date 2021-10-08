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

    override fun onApplicationConfigurationChange(applicationConfiguration: Bundle) { }

    // Curryware Comment:  Added the logging statements.  Trying to figure out what is in the
    // profile.
    override fun onApplicationProfileReceived(context: Context, profileId: String,
            appProfile: ApplicationProfile)
    {
        Log.i(logTag, "Profile ID: $profileId")
        Log.i(logTag, "Profile Name: ${appProfile.name}")
    }

    override fun onClearAppDataCommandReceived(context: Context, reasonCode: ClearReasonCode)
    { }

    override fun onAnchorAppStatusReceived(context: Context, appStatus: AnchorAppStatus) { }

    override fun onAnchorAppUpgrade(context: Context, isUpgrade: Boolean) { }
}