package org.curryware.androidsdk

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.curryware.androidsdk.databinding.FragmentFirstBinding
import org.curryware.androidsdk.viewmodels.SDKViewModel
import kotlin.math.log

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val logTag: String = "FirstFragment"
    private lateinit var textViewIsEnrolled: TextView
    private lateinit var textViewEnrolledUser: TextView
    private val sdkViewModel: SDKViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        textViewIsEnrolled = view.findViewById(R.id.is_enrolled)
        textViewEnrolledUser = view.findViewById(R.id.enrolled_user)

        val currentNameObserver = Observer<String> { currentNameValue ->
            textViewEnrolledUser.text = currentNameValue
        }
        sdkViewModel.liveDataCurrentName.observe(viewLifecycleOwner, currentNameObserver)
        Log.i(logTag, "SDK ViewModel: ${sdkViewModel.liveDataCurrentName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Looking for online documentation for the methods documented at:
    // file:///Users/scotc/Downloads/Android%20SDK%20v22.2/Documentation/Javadocs/Client%20SDK%20Javadoc/html/-a-w%20-client%20-s-d-k/com.airwatch.sdk/-s-d-k-manager/index.html
//    private fun startSDK() { thread {
//        try {
//            val initSDKManager = SDKManager.init(this.context)
//            sdkManager = initSDKManager
//            Log.i(logTag, "!!! SDK Manager Initialized !!!")
//            if (sdkManager != null) {
//                val enrolled = sdkManager!!.isEnrolled
//                textViewIsEnrolled.text = "Is Enrolled: $enrolled"
//                val enrolledUser = sdkManager!!.enrollmentUsername
//                textViewEnrolledUser.text = "Enrolled User: $enrolledUser"
//
//                val passcodePolicy = sdkManager!!.passcodePolicy
//                val customSettings = sdkManager!!.customSettings
//                val restrictionPolicy = sdkManager!!.restrictionPolicy
//                val apiVersion = sdkManager!!.apiVersion
//
//                val sdkDataModel = SDKDataModel(restrictionPolicy, passcodePolicy, customSettings)
//
//                Log.i(logTag, "Is Enrolled: $enrolled")
//                Log.i(logTag, "Enrolled User: $enrolledUser")
//            }
//        }
//        catch (exception: AirWatchSDKException) {
//            sdkManager = null
//            Log.e(logTag, "Exception ${exception.message}")
//        }
//    } }
}