package org.curryware.androidsdk

import android.Manifest
import android.content.Context
import android.content.RestrictionsManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.airwatch.sdk.SDKManager
import com.crittercism.app.Crittercism
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val logTag: String = "MainActivity"
    private lateinit var sdkInfoModel: SdkViewModel
    private var sdkManager: SDKManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewLastUpdate = findViewById<TextView>(R.id.textView_updateText)
        val textViewLoggedInUser = findViewById<TextView>(R.id.textView_loggedInUserFromSDK)
        val textViewAPIVersion = findViewById<TextView>(R.id.textView_apiVersionText)
        val textViewCustomSettings = findViewById<TextView>(R.id.textView_customSettingsText)
        val textViewOSVersion = findViewById<TextView>(R.id.textView_osVersionText)
        val textViewBuidVersion = findViewById<TextView>(R.id.textView_buildVersionText)
        val textViewAppConfig = findViewById<TextView>(R.id.textView_appConfigString)
        val initSDKButton = findViewById<Button>(R.id.button_initSDK)
        val initItelligence = findViewById<Button>(R.id.button_Intelligence)

        val versionName = BuildConfig.VERSION_NAME
        val osVersion = Build.VERSION.SDK_INT
        textViewBuidVersion.text = versionName
        textViewOSVersion.text = osVersion.toString()

        sdkInfoModel = ViewModelProvider(this).get(SdkViewModel::class.java)
        sdkInfoModel.lastUpdateTime.observe(this, {
            textViewLastUpdate.text = it.toString()
        })

        sdkInfoModel.loggedInUser.observe(this, {
            textViewLoggedInUser.text = it.toString()
        })

        sdkInfoModel.apiVersion.observe(this, {
            textViewAPIVersion.text = it.toString()
        })

        sdkInfoModel.customSettings.observe(this, {
            textViewCustomSettings.text = it.toString()
        })

        initSDKButton.setOnClickListener {
            val returnString = getAppConfigInfo()
            textViewAppConfig.text = returnString
            Log.i(logTag, "Starting SDK")
            getOSVersion()
            startSDK()
        }

        initItelligence.setOnClickListener {
            intelligenceCode()
            Log.i(logTag, "Clicked Intelligence Button")
        }
    }


    private fun intelligenceCode() {
        Crittercism.initialize(this, "c6751b9d5ea5431ea0515f3a403add2200555300")
        Crittercism.leaveBreadcrumb("Just Initialzed Critercism")
        Log.i(logTag, "Left a breadcrumb")
    }

    private fun startSDK() { thread {

        val currentDate = LocalDateTime.now()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")
        val dateString = currentDate.format(dateTimeFormatter)
        Log.i(logTag, "Update Time: $dateString")
        sdkInfoModel.lastUpdateTime.postValue(dateString)

        try {
            val initSDKManager = SDKManager.init(this)
            sdkManager = initSDKManager

            sdkInfoModel.loggedInUser.postValue(sdkManager?.enrollmentUsername)
            sdkInfoModel.apiVersion.postValue(sdkManager?.apiVersion)
            sdkInfoModel.customSettings.postValue(sdkManager?.customSettings)
            Log.i(logTag, "SDK Initialized Correctly")
        } catch (exception: Exception) {
            sdkManager = null
            if (exception.message != null) {
                val exceptionThrown = exception.message!!
                val exceptionMessage = "SDK Error: $exceptionThrown"
                Log.e(logTag, exceptionMessage, exception)
            }
        }
    } }

    private fun getOSVersion() : Int {

        val androidVersionNumber = Build.VERSION.SDK_INT
        Log.i(logTag, "OS Version: $androidVersionNumber")
        return androidVersionNumber
    }

    private fun getAppConfigInfo(): String? {

        val appConfigStringConst = "appConfigKeyString"
        val crittercismKeyConst = "crittercismKey"

        val restrictionsManager = this.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager
        val appRestrictions: Bundle = restrictionsManager.applicationRestrictions
        val configString = appRestrictions.getString(appConfigStringConst)
        val configStringConst = configString?: "NA"
        Log.i(logTag, "Configuration String: $configString")

        val configValues = mutableMapOf<String, String>()
        configValues.put(appConfigStringConst, configStringConst)

        val crittercismKey = appRestrictions.getString(crittercismKeyConst)
        val crittercismValue = crittercismKey?: "NA"
        configValues.put(crittercismKeyConst, crittercismValue)
        Log.i(logTag, "Crittercism Key: $crittercismValue")
        return configString
    }
}