package org.curryware.androidsdk

import android.content.Context
import android.os.Bundle
import android.os.UserManager
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.airwatch.sdk.AirWatchSDKException
import com.airwatch.sdk.SDKManager
import org.curryware.androidsdk.databinding.ActivityMainBinding
import org.curryware.androidsdk.viewmodels.SDKViewModel
import kotlin.concurrent.thread
import com.crittercism.app.Crittercism

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val UEM_USERNAME = "uem_username"
    private lateinit var sdkViewModel: SDKViewModel
    private val logTag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        sdkViewModel = ViewModelProvider(this).get(SDKViewModel::class.java)
        startSDK()

        Crittercism.initialize(applicationContext, "c6751b9d5ea5431ea0515f3a403add2200555300")
        val dataDirectory = applicationContext.applicationInfo.dataDir
        Crittercism.leaveBreadcrumb("Data Directory: $dataDirectory")
        startSDK()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onResume()  {
        super.onResume()

        val userManager : UserManager = getSystemService(Context.USER_SERVICE) as UserManager
        val restrictionsBundle = userManager.getApplicationRestrictions(packageName)


        if (restrictionsBundle.containsKey(UEM_USERNAME)) {
            val publicUEMUserName = restrictionsBundle.getString(UEM_USERNAME)
            Log.i(logTag, "UEM UserName: $publicUEMUserName")
        }
    }


    private fun logSDKStart() {
        Log.i(logTag, "Calling SDK Function in Thread!")
    }

    // This is where all of the SDK calls are made.  The setup is done in the AirWatchSDKIntentService
    // code.
    private fun startSDK() { thread {
        try {
            val initSDKManager = SDKManager.init(this)
            Log.i(logTag, "!!! SDK Manager Initialized !!!")
            if (initSDKManager != null) {

                val enrolled = initSDKManager.isEnrolled
                val enrolledUser = initSDKManager.enrollmentUsername
                val passcodePolicy = initSDKManager.passcodePolicy
                val customSettings = initSDKManager.customSettings
                val restrictionPolicy = initSDKManager.restrictionPolicy
                val apiVersion = initSDKManager.apiVersion

                Log.i(logTag, "Is Enrolled: $enrolled")
                Log.i(logTag, "Enrolled User: $enrolledUser")

                sdkViewModel.liveDataCurrentName.postValue(enrolledUser)
                sdkViewModel.passcodePolicy.postValue(passcodePolicy)
                sdkViewModel.customSettingsPolicy.postValue(customSettings)
                sdkViewModel.restrictionPolicy.postValue(restrictionPolicy)
                sdkViewModel.apiVersion.postValue(apiVersion)
                sdkViewModel.isEnrolled.postValue(enrolled)
            }
        }
        catch (exception: AirWatchSDKException) {
            Log.e(logTag, "Exception ${exception.message}")
        }
    } }
}