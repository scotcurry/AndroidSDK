package org.curryware.androidsdk

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.airwatch.event.SDKClientConfig
import com.airwatch.event.WS1AnchorEvents
import com.airwatch.sdk.shareddevice.ClearReasonCode
import java.security.cert.X509Certificate

// Note the fully qualified base class name.
open class AWApplication: com.airwatch.app.AWApplication(), SDKClientConfig {

    private val logTag: String = "AWApplication"

    override fun getMainActivityIntent(): Intent {
        Log.i(logTag, "Calling getMainActivityIntent")
        return Intent(applicationContext, MainActivity::class.java)
    }

    override fun onSSLPinningRequestFailure(host: String, serverCACert: X509Certificate?) {
        Log.i(logTag, "SSL Pinning Failure")
    }

    override fun onSSLPinningValidationFailure(host: String, serverCACert: X509Certificate?) {
        Log.i(logTag, "SSL Validation Failure")
    }

    override fun getNightMode(): Int {
        return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    override fun getEventHandler(): WS1AnchorEvents {
        return AirWatchSDKIntentService()
    }
}